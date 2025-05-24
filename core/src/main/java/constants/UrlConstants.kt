package constants


object BaseUrl {
//    const val BASE_URL = "http://localhost:8080/"
    const val BASE_URL = "https://todo-ufcz.onrender.com/"
}

object Endpoints {

    // Onboarding
    const val CHECK_USER_EXISTS = "user/exists"
    const val VERIFY_OTP = "user/email/otp/verify"
    const val REGISTER = "user/register"
    const val SEND_OTP = "user/email/otp"

    // User
    const val GET_USER = "user"

    // Tasks
    const val GET_TASKS = "/task/get"
    const val GET_TASK_BY_ID = "/task/getById"
    const val SAVE_TASK = "/task/save"
    const val TOGGLE_TASK_COMPLETION = "/task/completion"

}