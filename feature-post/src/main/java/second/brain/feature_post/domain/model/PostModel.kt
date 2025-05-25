package second.brain.feature_post.domain.model
import kotlinx.serialization.Serializable



@Serializable
data class Post(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val userProfileImage: String = "",
    val content: String = "",
    val imageUrl: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val likesCount: Int = 0,
    val likedBy: List<String> = emptyList(),
    val commentsCount: Int = 0
)

@Serializable
data class CreatePostRequest(
    val content: String,
    val imageUrl: String? = null,
    val name : String = "Buddy",
    val email : String = ""
)

@Serializable
data class PostResponse(
    val id: Int = 0,
    val message: String = "",
    val data: List<Post> = emptyList()
)