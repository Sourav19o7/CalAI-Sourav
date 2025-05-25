package second.brain.feature_post.data.repository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import data.repository.DataRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import network.Resource
import network.ResponseHandler
import second.brain.feature_post.domain.model.CreatePostRequest
import second.brain.feature_post.domain.model.Post
import second.brain.feature_post.domain.repository.PostsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val responseHandler: ResponseHandler,
    private val dataRepository: DataRepository
) : PostsRepository {

    companion object {
        private const val POSTS_COLLECTION = "posts"
        private const val TAG = "PostsRepository"
    }

    override suspend fun createPost(post: CreatePostRequest): Resource<Post> {
        return try {
            val currentUser = dataRepository.getData().first()

            val newPost = Post(
                id = "",
                userId = currentUser.userId ?: "",
                userName = currentUser.name ?: "",
                userProfileImage = currentUser.profileImage ?: "",
                content = post.content,
                imageUrl = post.imageUrl,
                timestamp = System.currentTimeMillis(),
                likesCount = 0,
                likedBy = emptyList(),
                commentsCount = 0,
                userActive = true
            )

            val docRef = firestore.collection(POSTS_COLLECTION).document()
            val postWithId = newPost.copy(id = docRef.id)

            docRef.set(postWithId).await()

            responseHandler.handleSuccess(postWithId)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating post", e)
            responseHandler.handleException<Post>(e, "", "createPost", "createPost")
        }
    }

    override suspend fun getAllPosts(): Resource<List<Post>> {
        return try {
            val querySnapshot = firestore.collection(POSTS_COLLECTION)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            val posts = querySnapshot.documents.mapNotNull { document ->
                try {
                    document.toObject(Post::class.java)?.copy(id = document.id)
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing post: ${document.id}", e)
                    null
                }
            }

            responseHandler.handleSuccess(posts)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting posts", e)
            responseHandler.handleException<List<Post>>(e, "", "getAllPosts", "getAllPosts")
        }
    }

    override suspend fun getUserPosts(userId: String): Resource<List<Post>> {
        return try {
            val querySnapshot = firestore.collection(POSTS_COLLECTION)
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            val posts = querySnapshot.documents.mapNotNull { document ->
                try {
                    document.toObject(Post::class.java)?.copy(id = document.id)
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing user post: ${document.id}", e)
                    null
                }
            }

            responseHandler.handleSuccess(posts)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user posts", e)
            responseHandler.handleException<List<Post>>(e, "", "getUserPosts", "getUserPosts")
        }
    }

    override suspend fun likePost(postId: String, userId: String): Resource<Boolean> {
        return try {
            val postRef = firestore.collection(POSTS_COLLECTION).document(postId)

            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(postRef)
                val post = snapshot.toObject(Post::class.java)

                if (post != null && !post.likedBy.contains(userId)) {
                    transaction.update(postRef, mapOf(
                        "likedBy" to FieldValue.arrayUnion(userId),
                        "likesCount" to FieldValue.increment(1)
                    ))
                }
            }.await()

            responseHandler.handleSuccess(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error liking post", e)
            responseHandler.handleException<Boolean>(e, "", "likePost", "likePost")
        }
    }

    override suspend fun unlikePost(postId: String, userId: String): Resource<Boolean> {
        return try {
            val postRef = firestore.collection(POSTS_COLLECTION).document(postId)

            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(postRef)
                val post = snapshot.toObject(Post::class.java)

                if (post != null && post.likedBy.contains(userId)) {
                    transaction.update(postRef, mapOf(
                        "likedBy" to FieldValue.arrayRemove(userId),
                        "likesCount" to FieldValue.increment(-1)
                    ))
                }
            }.await()

            responseHandler.handleSuccess(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error unliking post", e)
            responseHandler.handleException<Boolean>(e, "", "unlikePost", "unlikePost")
        }
    }

    override suspend fun deletePost(postId: String, userId: String): Resource<Boolean> {
        return try {
            val postRef = firestore.collection(POSTS_COLLECTION).document(postId)
            val snapshot = postRef.get().await()
            val post = snapshot.toObject(Post::class.java)

            if (post?.userId == userId) {
                postRef.delete().await()
                Log.d(TAG, "Post deleted: $postId")
                responseHandler.handleSuccess(true)
            } else {
                Log.w(TAG, "User $userId attempted to delete post $postId they don't own")
                responseHandler.handleSuccess(false)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting post", e)
            responseHandler.handleException<Boolean>(e, "", "deletePost", "deletePost")
        }
    }

    override suspend fun setUserActiveStatus(userId: String, isActive: Boolean): Resource<Boolean> {
        return try {
            val querySnapshot = firestore.collection(POSTS_COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val batch = firestore.batch()

            querySnapshot.documents.forEach { document ->
                batch.update(document.reference, "userActive", isActive)
            }

            batch.commit().await()

            Log.d(TAG, "Updated userActive status to $isActive for ${querySnapshot.size()} posts by user: $userId")
            responseHandler.handleSuccess(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating user active status", e)
            responseHandler.handleException<Boolean>(e, "", "setUserActiveStatus", "setUserActiveStatus")
        }
    }

    override fun getPostsFlow(): Flow<List<Post>> = callbackFlow {
        val listener = firestore.collection(POSTS_COLLECTION)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error listening to posts", error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val posts = snapshot.documents.mapNotNull { document ->
                        try {
                            document.toObject(Post::class.java)?.copy(id = document.id)
                        } catch (e: Exception) {
                            Log.e(TAG, "Error parsing post in flow: ${document.id}", e)
                            null
                        }
                    }
                    trySend(posts.filter { it.userActive })
                }
            }

        awaitClose { listener.remove() }
    }
}