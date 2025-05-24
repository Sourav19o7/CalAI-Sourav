package second.brain.feature_onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.DataPreferencesManager
import data.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import network.Status
import second.brain.feature_onboarding.domain.repository.OnboardingRepository
import second.brain.feature_onboarding.presentation.event.OnboardingEvent
import second.brain.feature_onboarding.presentation.state.OnboardingState
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val dataRepository: DataRepository,
    private val dataPreferencesManager: DataPreferencesManager
) : ViewModel() {
    private val _state = MutableStateFlow(OnboardingState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    userLoggedIn = dataPreferencesManager.isLoggedIn
                )
            }
        }
    }

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.SignInSuccess -> {
                _state.update {
                    it.copy(
                        isSignInSuccessful = event.signInResult.data != null,
                        signInErrorMessage = event.signInResult.errorMessage
                    )
                }

                if (event.signInResult.data != null) {
                    _state.update {
                        it.copy(
                            email = event.signInResult.data.email,
                            name = event.signInResult.data.name,
                            photoUrl = event.signInResult.data.photoUrl
                        )
                    }
                }
            }

            OnboardingEvent.ResetGoogleState -> {
                _state.update {
                    it.copy(
                        isSignInSuccessful = false,
                        signInErrorMessage = null
                    )
                }
            }
        }
    }

    private fun userLoggedIn() {
        dataPreferencesManager.isLoggedIn = true
    }
}