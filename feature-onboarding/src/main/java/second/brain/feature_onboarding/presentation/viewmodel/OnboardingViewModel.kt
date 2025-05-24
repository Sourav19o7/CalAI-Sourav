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
import utils.JwtBuilder
import utils.Validator
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val jwtBuilder: JwtBuilder,
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

            is OnboardingEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        isEmailValid = Validator.isValidEmail(event.email)
                    )
                }
            }

            is OnboardingEvent.NameChanged -> {
                _state.update {
                    it.copy(
                        name = event.name,
                        isNameValid = Validator.isValidName(event.name)
                    )
                }
            }

            is OnboardingEvent.PhoneNumberChanged -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber,
                        isPhoneNumberValid = Validator.isValidPhone(event.phoneNumber)
                    )
                }
            }

            is OnboardingEvent.OTPChanged -> {
                _state.update {
                    it.copy(
                        otp = event.otp
                    )
                }
            }

            is OnboardingEvent.CheckUserExists -> {

                viewModelScope.launch {
                    val jwtToken = jwtBuilder.createJwt(
                        mapOf(
                            "email" to _state.value.email,
                        )
                    )

                    val userExists = onboardingRepository.checkUserExists(jwtToken)

                    when (userExists.status) {
                        Status.SUCCESS -> {
                            event.userExists(userExists.data?.id == 1)
                            userLoggedIn()
                            userExists.data?.data?.let {
                                dataRepository.updateData(
                                    it
                                )
                            }
                        }

                        else -> {

                        }
                    }
                }
            }

            is OnboardingEvent.VerifyOTP -> {
                viewModelScope.launch {
                    val jwtToken = jwtBuilder.createJwt(
                        mapOf(
                            "email" to _state.value.email,
                            "otp" to _state.value.otp
                        )
                    )

                    val otpVerification = onboardingRepository.verifyOTP(jwtToken)

                    when (otpVerification.status) {
                        Status.SUCCESS -> {
                            userLoggedIn()
                            event.otpVerified(otpVerification.data?.id == 1)
                        }

                        else -> {

                        }
                    }
                }
            }

            is OnboardingEvent.RegisterUser -> {
                viewModelScope.launch {
                    val jwtToken = jwtBuilder.createJwt(
                        mapOf(
                            "email" to _state.value.email,
                            "name" to _state.value.name,
                            "phone" to _state.value.phoneNumber
                        )
                    )

                    val userRegistered = onboardingRepository.registerUser(jwtToken)

                    when (userRegistered.status) {
                        Status.SUCCESS -> {
                            userLoggedIn()
                            event.userRegistered(userRegistered.data?.id == 1)
                        }

                        else -> {

                        }
                    }
                }
            }

            OnboardingEvent.SendOTP -> {
                viewModelScope.launch {
                    val jwtToken = jwtBuilder.createJwt(
                        mapOf(
                            "email" to _state.value.email,
                        )
                    )

                    onboardingRepository.sendOtp(jwtToken)

                }
            }
        }
    }

    private fun userLoggedIn() {
        dataPreferencesManager.isLoggedIn = true
    }
}