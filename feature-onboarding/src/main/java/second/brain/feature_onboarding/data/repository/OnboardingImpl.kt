package second.brain.feature_onboarding.data.repository

import network.ResponseHandler
import second.brain.feature_onboarding.data.service.OnboardingApiService
import second.brain.feature_onboarding.domain.repository.OnboardingRepository

class OnboardingImpl(
    private val apiService: OnboardingApiService,
    private val responseHandler: ResponseHandler
) : OnboardingRepository {
}