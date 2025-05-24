package second.brain.main_navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import constants.ScreenConstants
import extension.sharedViewModel
import second.brain.feature_add_tasks.presentation.navigation.taskNavigation
import second.brain.feature_add_tasks.presentation.ui.AddTaskScreen
import second.brain.feature_add_tasks.presentation.viewmodel.TaskViewModel
import second.brain.feature_dashboard.presentation.event.DashboardEvent
import second.brain.feature_dashboard.presentation.ui.DashboardScreen
import second.brain.feature_dashboard.presentation.viewmodel.DashboardViewModel
import second.brain.feature_onboarding.presentation.navigation.onboardingNavigation
import second.brain.feature_user_tasks.presentation.navigation.userTasksNavigation

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = ScreenConstants.OnboardingParent) {

        onboardingNavigation(
            navController = navController
        )

        composable<ScreenConstants.HomeScreen> {

            val viewModel = it.sharedViewModel<DashboardViewModel>(navController = navController)
            val state by viewModel.state.collectAsState()

            LaunchedEffect(key1 = Unit) {
                if (state.tasks.isEmpty()) {
                    viewModel.onEvent(DashboardEvent.GetAllTasks)
                }
            }

            DashboardScreen(
                state = state
            ) { screenName ->
                navController.navigate(
                    screenName
                )
            }
        }

        taskNavigation(navController = navController)

        userTasksNavigation(navController = navController)

    }
}