package second.brain.feature_onboarding.data.service

import constants.Endpoints.CHECK_USER_EXISTS
import constants.Endpoints.REGISTER
import constants.Endpoints.SEND_OTP
import constants.Endpoints.VERIFY_OTP
import data.models.CommonResponse
import data.models.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OnboardingApiService {

    @GET(CHECK_USER_EXISTS)
    suspend fun checkUserExists(@Header("authorization") jwtToken : String) : UserResponse?

    @POST(VERIFY_OTP)
    suspend fun verifyOTP(@Header("authorization") jwtToken : String) : CommonResponse?

    @POST(REGISTER)
    suspend fun registerUser(@Header("authorization") jwtToken: String): CommonResponse?

    @POST(SEND_OTP)
    suspend fun sendOtp(@Header("authorization") jwtToken: String): CommonResponse?

}