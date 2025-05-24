package second.brain.feature_onboarding.presentation.navigation

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.android.gms.auth.api.identity.Identity
import constants.ScreenConstants
import extension.sharedViewModel
import kotlinx.coroutines.launch
import second.brain.feature_onboarding.presentation.authentication.GoogleAuthClient
import second.brain.feature_onboarding.presentation.event.OnboardingEvent
import second.brain.feature_onboarding.presentation.ui.IntroScreen
import second.brain.feature_onboarding.presentation.ui.OnboardingScreen
import second.brain.feature_onboarding.presentation.ui.SignUpScreen
import second.brain.feature_onboarding.presentation.ui.UserDetailsScreen
import second.brain.feature_onboarding.presentation.ui.VerifyOTP
import second.brain.feature_onboarding.presentation.viewmodel.OnboardingViewModel

fun NavGraphBuilder.onboardingNavigation(
    navController: NavHostController
) {

    navigation<ScreenConstants.OnboardingParent>(
        startDestination = ScreenConstants.IntroScreen
    ) {
        composable<ScreenConstants.IntroScreen> {
            val viewModel = it.sharedViewModel<OnboardingViewModel>(navController = navController)
            val state by viewModel.state.collectAsState()

            IntroScreen(
                userLoggedIn = state.userLoggedIn
            ) {
                navController.navigate(
                    it
                )
            }
        }
        composable<ScreenConstants.OnboardingScreen> {
            val viewModel = it.sharedViewModel<OnboardingViewModel>(navController = navController)
            val state by viewModel.state.collectAsState()
            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current
            val googleAuthClient by lazy {
                GoogleAuthClient(
                    context = context,
                    oneTapClient = Identity.getSignInClient(context)
                )
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        coroutineScope.launch {
                            val sighInResult = googleAuthClient.getSignInResult(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onEvent(
                                OnboardingEvent.SignInSuccess(
                                    signInResult = sighInResult
                                )
                            )
                        }
                    }
                }
            )

            LaunchedEffect(state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {

                    // Resetting States, in case user comes back to this screen
                    viewModel.onEvent(
                        OnboardingEvent.ResetGoogleState
                    )

                    viewModel.onEvent(
                        OnboardingEvent.CheckUserExists{ userExists ->
                            if (userExists) {
                                navController.navigate(
                                    ScreenConstants.HomeScreen
                                )
                            } else{
                                navController.navigate(
                                    ScreenConstants.UserDetails
                                )
                            }
                        }
                    )
                }
            }

            OnboardingScreen(
                state = state,
                signInWithGoogle = {
                    coroutineScope.launch {
                        val intentSender = googleAuthClient.signIn()
                        intentSender?.let {
                            launcher.launch(
                                IntentSenderRequest.Builder(intentSender)
                                    .build()
                            )
                        }
                    }
                },
                navigateTo = { screen ->
                    navController.navigate(
                        screen
                    )
                }
            )
        }

        composable<ScreenConstants.SignUp> {

            val viewModel = it.sharedViewModel<OnboardingViewModel>(navController = navController)
            val state by viewModel.state.collectAsState()

            SignUpScreen(
                state = state,
                onBack = {
                    navController.popBackStack()
                },
                onEvent = viewModel::onEvent,
                navigateTo = { screenName ->
                    navController.navigate(
                        screenName
                    )
                }
            )
        }

        composable<ScreenConstants.UserDetails> {

            val viewModel = it.sharedViewModel<OnboardingViewModel>(navController = navController)
            val state by viewModel.state.collectAsState()

            UserDetailsScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onBack = {
                    navController.popBackStack()
                },
                navigateTo = { screenName ->
                    navController.navigate(
                        screenName
                    )
                }
            )
        }

        composable<ScreenConstants.VerifyOTP> {

            val viewModel = it.sharedViewModel<OnboardingViewModel>(navController = navController)
            val state by viewModel.state.collectAsState()

            VerifyOTP(
                state = state,
                onBack = {
                    navController.popBackStack()
                },
                onEvent = viewModel::onEvent,
                navigateTo = { screenName ->
                    navController.navigate(
                        screenName
                    )
                }
            )
        }
    }
}