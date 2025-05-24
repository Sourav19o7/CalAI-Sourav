package second.brain.main_navigator

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import constants.ScreenConstants
import extension.sharedViewModel
import kotlinx.coroutines.launch
import second.brain.feature_onboarding.presentation.authentication.GoogleAuthClient
import second.brain.feature_onboarding.presentation.event.OnboardingEvent
import second.brain.feature_onboarding.presentation.navigation.onboardingNavigation
import second.brain.feature_onboarding.presentation.ui.OnboardingScreen
import second.brain.feature_onboarding.presentation.viewmodel.OnboardingViewModel
import second.brain.feature_post.domain.model.Post
import second.brain.feature_post.presentation.event.PostsEvent
import second.brain.feature_post.presentation.ui.PostsListScreen
import second.brain.feature_post.presentation.viewmodel.PostsViewModel

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = ScreenConstants.OnboardingParent) {

        onboardingNavigation(
            navController = navController
        )

        composable<ScreenConstants.PostListScreen> {
            val viewModel = it.sharedViewModel<PostsViewModel>(navController = navController)

            LaunchedEffect(key1 = Unit) {
                viewModel.onEvent(PostsEvent.LoadPosts)
            }
            PostsListScreen(
                viewModel = viewModel,
                onCreatePostClick = {
                    viewModel.onEvent(PostsEvent.CreatePost(
                       "Test"
                    ))
                }
            )
        }

    }
}