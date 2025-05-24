package second.brain.feature_onboarding.presentation.event

import second.brain.feature_onboarding.presentation.authentication.SignInResult

sealed interface OnboardingEvent {

    data class SignInSuccess(val signInResult: SignInResult) : OnboardingEvent

    data object ResetGoogleState : OnboardingEvent
}