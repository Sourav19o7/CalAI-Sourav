package second.brain.feature_post.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import network.Status
import second.brain.feature_post.domain.model.CreatePostRequest
import second.brain.feature_post.domain.repository.PostsRepository
import second.brain.feature_post.presentation.event.PostsEvent
import second.brain.feature_post.presentation.state.PostsState
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    companion object {
        private const val TAG = "PostsViewModel"
    }

    init {
        initializeCurrentUser()
        loadPosts()
        observePostsRealTime()
    }

    private fun initializeCurrentUser() {
        viewModelScope.launch {
            try {
                val currentUser = dataRepository.getData().first()
                _state.update {
                    it.copy(currentUserId = currentUser.userId ?: "")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting current user", e)
            }
        }
    }

    private fun observePostsRealTime() {
        postsRepository.getPostsFlow()
            .onEach { posts ->
                _state.update {
                    it.copy(
                        posts = posts,
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: PostsEvent) {
        when (event) {
            is PostsEvent.LoadPosts -> {
                loadPosts()
            }

            is PostsEvent.RefreshPosts -> {
                refreshPosts()
            }

            is PostsEvent.CreatePost -> {
                createPost(event.content, event.imageUrl)
            }

            is PostsEvent.LikePost -> {
                likePost(event.postId)
            }

            is PostsEvent.UnlikePost -> {
                unlikePost(event.postId)
            }

            is PostsEvent.DeletePost -> {
                deletePost(event.postId)
            }

            is PostsEvent.LoadUserPosts -> {
                loadUserPosts(event.userId)
            }
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val result = postsRepository.getAllPosts()

            when (result.status) {
                Status.SUCCESS -> {
                    _state.update {
                        it.copy(
                            posts = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    Log.d(TAG, "Posts loaded successfully: ${result.data?.size} posts")
                }

                Status.ERROR -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "Failed to load posts"
                        )
                    }
                    Log.e(TAG, "Error loading posts: ${result.message}")
                }

                Status.LOADING -> {
                }
            }
        }
    }

    private fun refreshPosts() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true, errorMessage = null) }

            val result = postsRepository.getAllPosts()

            when (result.status) {
                Status.SUCCESS -> {
                    _state.update {
                        it.copy(
                            posts = result.data ?: emptyList(),
                            isRefreshing = false
                        )
                    }
                    Log.d(TAG, "Posts refreshed successfully")
                }

                Status.ERROR -> {
                    _state.update {
                        it.copy(
                            isRefreshing = false,
                            errorMessage = result.message ?: "Failed to refresh posts"
                        )
                    }
                    Log.e(TAG, "Error refreshing posts: ${result.message}")
                }

                Status.LOADING -> {
                }
            }
        }
    }

    private fun createPost(content: String, imageUrl: String?) {
        if (content.isBlank()) {
            _state.update { it.copy(errorMessage = "Post content cannot be empty") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isCreatingPost = true, errorMessage = null) }

            val createPostRequest = CreatePostRequest(
                content = content.trim(),
                imageUrl = imageUrl
            )

            val result = postsRepository.createPost(createPostRequest)

            when (result.status) {
                Status.SUCCESS -> {
                    _state.update {
                        it.copy(isCreatingPost = false)
                    }
                    Log.d(TAG, "Post created successfully")
                }

                Status.ERROR -> {
                    _state.update {
                        it.copy(
                            isCreatingPost = false,
                            errorMessage = result.message ?: "Failed to create post"
                        )
                    }
                    Log.e(TAG, "Error creating post: ${result.message}")
                }

                Status.LOADING -> {
                }
            }
        }
    }

    private fun likePost(postId: String) {
        val currentUserId = _state.value.currentUserId
        if (currentUserId.isEmpty()) {
            Log.w(TAG, "Cannot like post: No current user")
            return
        }

        viewModelScope.launch {
            val result = postsRepository.likePost(postId, currentUserId)

            if (result.status == Status.ERROR) {
                _state.update {
                    it.copy(errorMessage = result.message ?: "Failed to like post")
                }
                Log.e(TAG, "Error liking post: ${result.message}")
            }
        }
    }

    private fun unlikePost(postId: String) {
        val currentUserId = _state.value.currentUserId
        if (currentUserId.isEmpty()) {
            Log.w(TAG, "Cannot unlike post: No current user")
            return
        }

        viewModelScope.launch {
            val result = postsRepository.unlikePost(postId, currentUserId)

            if (result.status == Status.ERROR) {
                _state.update {
                    it.copy(errorMessage = result.message ?: "Failed to unlike post")
                }
                Log.e(TAG, "Error unliking post: ${result.message}")
            }
        }
    }

    private fun deletePost(postId: String) {
        val currentUserId = _state.value.currentUserId
        if (currentUserId.isEmpty()) {
            Log.w(TAG, "Cannot delete post: No current user")
            return
        }

        viewModelScope.launch {
            val result = postsRepository.deletePost(postId, currentUserId)

            when (result.status) {
                Status.SUCCESS -> {
                    if (result.data == true) {
                        Log.d(TAG, "Post deleted successfully")
                    } else {
                        _state.update {
                            it.copy(errorMessage = "You can only delete your own posts")
                        }
                    }
                }

                Status.ERROR -> {
                    _state.update {
                        it.copy(errorMessage = result.message ?: "Failed to delete post")
                    }
                    Log.e(TAG, "Error deleting post: ${result.message}")
                }

                Status.LOADING -> {
                }
            }
        }
    }

    private fun loadUserPosts(userId: String) {
        viewModelScope.launch {
            val result = postsRepository.getUserPosts(userId)

            when (result.status) {
                Status.SUCCESS -> {
                    _state.update {
                        it.copy(userPosts = result.data ?: emptyList())
                    }
                    Log.d(TAG, "User posts loaded successfully: ${result.data?.size} posts")
                }

                Status.ERROR -> {
                    _state.update {
                        it.copy(errorMessage = result.message ?: "Failed to load user posts")
                    }
                    Log.e(TAG, "Error loading user posts: ${result.message}")
                }

                Status.LOADING -> {
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}