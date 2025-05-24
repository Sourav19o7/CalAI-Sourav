package second.brain.feature_post.presentation.state

import second.brain.feature_post.domain.model.Post

data class PostsState(
    val posts: List<Post> = emptyList(),
    val userPosts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isCreatingPost: Boolean = false,
    val errorMessage: String? = null,
    val currentUserId: String = ""
)