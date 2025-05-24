package second.brain.feature_onboarding.presentation.event

import second.brain.feature_onboarding.presentation.authentication.SignInResult

sealed interface OnboardingEvent {

    data class SignInSuccess(val signInResult: SignInResult) : OnboardingEvent

    data object ResetGoogleState : OnboardingEvent

    data class EmailChanged(val email : String) : OnboardingEvent

    data class NameChanged(val name : String) : OnboardingEvent

    data class PhoneNumberChanged(val phoneNumber : String) : OnboardingEvent

    data class OTPChanged(val otp : String) : OnboardingEvent

    data class CheckUserExists(val userExists : (Boolean) -> Unit) : OnboardingEvent

    data object SendOTP : OnboardingEvent

    data class VerifyOTP(val otpVerified : (Boolean) -> Unit) : OnboardingEvent

    data class RegisterUser(val userRegistered : (Boolean) -> Unit) : OnboardingEvent
}