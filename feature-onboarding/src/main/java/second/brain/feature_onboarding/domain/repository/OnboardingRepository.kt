package second.brain.feature_onboarding.domain.repository

import data.models.CommonResponse
import data.models.UserResponse
import network.Resource

interface OnboardingRepository {
    suspend fun checkUserExists(jwtToken: String): Resource<UserResponse>

    suspend fun verifyOTP(jwtToken: String): Resource<CommonResponse>

    suspend fun registerUser(jwtToken: String): Resource<CommonResponse>

    suspend fun sendOtp(jwtToken: String) : Resource<CommonResponse>
}