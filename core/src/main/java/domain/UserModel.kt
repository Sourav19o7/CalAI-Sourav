package domain

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    @SerializedName("id") val columnId: Int? = 0,
    @SerializedName("user_id") val userId: String? = "",
    @SerializedName("full_name") val name: String? = "",
    @SerializedName("email_address") val email: String? = "",
    @SerializedName("phone_number") val phoneNumber: String? = "",
    @SerializedName("is_email_verified") val isEmailVerified: Boolean? = false,
    @SerializedName("is_phone_verified") val isPhoneVerified: Boolean? = false,
    @SerializedName("notification_id") val notificationId: String? = "",
    @SerializedName("profile_image_url") val profileImage: String? = "",
    @SerializedName("version_code") val versionCode: Int? = 0,
    @SerializedName("banned") val banned: Boolean? = false,
    @SerializedName("created_at") val createdAt: String? = "",
)
