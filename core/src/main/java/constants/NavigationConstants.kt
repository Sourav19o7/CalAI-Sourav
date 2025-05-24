package constants

import kotlinx.serialization.Serializable

@Serializable
sealed interface ScreenConstants {

    @Serializable
    data object OnboardingParent : ScreenConstants

    @Serializable
    data object IntroScreen : ScreenConstants

    @Serializable
    data object OnboardingScreen : ScreenConstants

    @Serializable
    data object SignUp : ScreenConstants

    @Serializable
    data object VerifyOTP : ScreenConstants

    @Serializable
    data object UserDetails : ScreenConstants


    @Serializable
    data object HomeScreen : ScreenConstants

    @Serializable
    data class TaskParent (val taskId : Int = 0) : ScreenConstants

    @Serializable
    data object AddTask : ScreenConstants

    @Serializable
    data object DueDate : ScreenConstants

    @Serializable
    data object AssignedTo : ScreenConstants

    @Serializable
    data object AIPlanner : ScreenConstants

    @Serializable
    data object UserTaskParent : ScreenConstants

    @Serializable
    data object UserTasks : ScreenConstants

}