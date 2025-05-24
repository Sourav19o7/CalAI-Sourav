package second.brain.feature_post.presentation.event


sealed interface PostsEvent {
    data object LoadPosts : PostsEvent
    data object RefreshPosts : PostsEvent
    data class CreatePost(val content: String, val imageUrl: String? = null) : PostsEvent
    data class LikePost(val postId: String) : PostsEvent
    data class UnlikePost(val postId: String) : PostsEvent
    data class DeletePost(val postId: String) : PostsEvent
    data class LoadUserPosts(val userId: String) : PostsEvent
}