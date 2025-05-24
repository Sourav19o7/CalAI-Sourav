package constants

import kotlinx.serialization.Serializable

@Serializable
sealed interface ScreenConstants {

    @Serializable
    data object OnboardingParent : ScreenConstants

    @Serializable
    data object OnboardingScreen : ScreenConstants

    @Serializable
    data object PostListScreen : ScreenConstants

}