package second.brain.feature_onboarding.di

import constants.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import network.ResponseHandler
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import second.brain.feature_onboarding.data.repository.OnboardingImpl
import second.brain.feature_onboarding.data.service.OnboardingApiService
import second.brain.feature_onboarding.domain.repository.OnboardingRepository

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingModule {

    @Provides
    @ViewModelScoped
    fun provideOnboardingRepo(
        apiService: OnboardingApiService,
        responseHandler: ResponseHandler,
    ): OnboardingRepository {
        return OnboardingImpl(apiService, responseHandler)
    }

    @Provides
    @ViewModelScoped
    fun providesOnboardingApi(): OnboardingApiService {
        return Retrofit.Builder()
            .baseUrl(BaseUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OnboardingApiService::class.java)
    }
}