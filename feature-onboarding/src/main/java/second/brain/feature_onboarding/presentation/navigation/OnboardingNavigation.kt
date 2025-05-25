package second.brain.feature_onboarding.presentation.navigation

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import second.brain.feature_onboarding.presentation.ui.OnboardingScreen
import second.brain.feature_onboarding.presentation.viewmodel.OnboardingViewModel

fun NavGraphBuilder.onboardingNavigation(
    navController: NavHostController,
    setUserActive : () -> Unit
) {

    navigation<ScreenConstants.OnboardingParent>(
        startDestination = ScreenConstants.OnboardingScreen
    ) {
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

            var navigated by remember {
                mutableStateOf(false)
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

                    viewModel.onEvent(
                        OnboardingEvent.ResetGoogleState
                    )

                    if (!navigated) {
                        navigated = true
                        setUserActive()
                        navController.popBackStack()
                        navController.navigate(
                            ScreenConstants.PostListScreen
                        )
                    }

                }
            }

            LaunchedEffect(key1 = state.userLoggedIn) {
                if(state.userLoggedIn){
                    Log.i(
                        "Coming from Here",
                        "jhgf"
                    )
                    navController.popBackStack()
                    navController.navigate(
                        ScreenConstants.PostListScreen
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
                }
            )
        }
    }
}