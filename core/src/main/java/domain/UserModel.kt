package domain

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    @SerializedName("user_id") val userId: String? = "",
    @SerializedName("full_name") val name: String? = "",
    @SerializedName("email_address") val email: String? = "",
    @SerializedName("phone_number") val phoneNumber: String? = "",
    @SerializedName("notification_id") val notificationId: String? = "",
    @SerializedName("profile_image_url") val profileImage: String? = "",
    @SerializedName("version_code") val versionCode: Int? = 0,
    @SerializedName("banned") val banned: Boolean? = false,
    @SerializedName("created_at") val createdAt: String? = "",
)
