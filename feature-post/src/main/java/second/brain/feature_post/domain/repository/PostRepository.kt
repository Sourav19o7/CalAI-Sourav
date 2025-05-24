package second.brain.feature_post.domain.repository

import kotlinx.coroutines.flow.Flow
import network.Resource
import second.brain.feature_post.domain.model.CreatePostRequest
import second.brain.feature_post.domain.model.Post

interface PostsRepository {
    suspend fun createPost(post: CreatePostRequest): Resource<Post>
    suspend fun getAllPosts(): Resource<List<Post>>
    suspend fun getUserPosts(userId: String): Resource<List<Post>>
    suspend fun likePost(postId: String, userId: String): Resource<Boolean>
    suspend fun unlikePost(postId: String, userId: String): Resource<Boolean>
    suspend fun deletePost(postId: String, userId: String): Resource<Boolean>
    fun getPostsFlow(): Flow<List<Post>>
}