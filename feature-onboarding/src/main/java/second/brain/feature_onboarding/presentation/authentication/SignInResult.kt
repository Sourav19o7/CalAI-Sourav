package second.brain.feature_onboarding.presentation.authentication

data class SignInResult(
    val data : UserData?,
    val errorMessage : String? = null
)

data class UserData(
    val email : String,
    val name : String,
    val id : String,
    val photoUrl : String
)
