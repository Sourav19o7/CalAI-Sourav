package second.brain.main_navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import constants.ScreenConstants
import second.brain.feature_onboarding.presentation.navigation.onboardingNavigation

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = ScreenConstants.OnboardingParent) {

        onboardingNavigation(
            navController = navController
        )

    }
}