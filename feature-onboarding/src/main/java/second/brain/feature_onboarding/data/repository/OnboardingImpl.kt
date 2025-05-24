package second.brain.feature_onboarding.data.repository

import constants.Endpoints
import data.models.CommonResponse
import data.models.UserResponse
import network.Resource
import network.ResponseHandler
import second.brain.feature_onboarding.data.service.OnboardingApiService
import second.brain.feature_onboarding.domain.repository.OnboardingRepository

class OnboardingImpl(
    private val apiService: OnboardingApiService,
    private val responseHandler: ResponseHandler
) : OnboardingRepository {


    override suspend fun checkUserExists(jwtToken: String): Resource<UserResponse> {
        return try {
            responseHandler.handleSuccess(apiService.checkUserExists(jwtToken))
        } catch (exception: Exception) {
            responseHandler.handleException(
                exception,
                jwtToken,
                Endpoints.CHECK_USER_EXISTS,
                "checkUserExists"
            )
        }
    }

    override suspend fun verifyOTP(jwtToken: String): Resource<CommonResponse> {
        return try {
            responseHandler.handleSuccess(apiService.verifyOTP(jwtToken))
        } catch (exception: Exception) {
            responseHandler.handleException(
                exception,
                jwtToken,
                Endpoints.VERIFY_OTP,
                "verifyOTP"
            )
        }
    }

    override suspend fun registerUser(jwtToken: String): Resource<CommonResponse> {
        return try {
            responseHandler.handleSuccess(apiService.registerUser(jwtToken))
        } catch (exception: Exception) {
            responseHandler.handleException(
                exception,
                jwtToken,
                Endpoints.REGISTER,
                "registerUser"
            )
        }
    }

    override suspend fun sendOtp(jwtToken: String): Resource<CommonResponse> {
        return try {
            responseHandler.handleSuccess(apiService.sendOtp(jwtToken))
        } catch (exception: Exception) {
            responseHandler.handleException(
                exception,
                jwtToken,
                Endpoints.SEND_OTP,
                "sendOtp"
            )
        }
    }
}