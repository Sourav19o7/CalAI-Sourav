package second.brain.feature_onboarding.presentation.state

data class OnboardingState (

    // Google Signing states
    val isSignInSuccessful: Boolean = false,
    val signInErrorMessage: String? = null,

    // User Details states
    val name : String = "",
    val email : String = "",
    val photoUrl : String = "",
    val phoneNumber : String = "",
    val otp : String = "",

    // Details validity states
    val isEmailValid : Boolean = false,
    val isPhoneNumberValid : Boolean = false,
    val isNameValid : Boolean = false,

    // UI states
    val userLoggedIn : Boolean = false,
    val userExists : Boolean = false,


)