package second.brain.main_navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import constants.ScreenConstants
import extension.sharedViewModel
import second.brain.feature_onboarding.presentation.navigation.onboardingNavigation
import second.brain.feature_post.presentation.event.PostsEvent
import second.brain.feature_post.presentation.ui.PostsListScreen
import second.brain.feature_post.presentation.viewmodel.PostsViewModel

@Composable
fun AppNavHost(navController: NavHostController, setUserActive: () -> Unit) {

    NavHost(navController = navController, startDestination = ScreenConstants.OnboardingParent) {

        onboardingNavigation(
            navController = navController,
            setUserActive = setUserActive
        )

        composable<ScreenConstants.PostListScreen> {
            val viewModel = it.sharedViewModel<PostsViewModel>(navController = navController)

            LaunchedEffect(key1 = Unit) {
                viewModel.onEvent(PostsEvent.LoadPosts)
            }
            PostsListScreen(
                viewModel = viewModel,
                onCreatePostClick = {
                    viewModel.onEvent(
                        PostsEvent.CreatePost(
                            it
                        )
                    )
                }
            )
        }

    }
}