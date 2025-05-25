package second.brain

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import constants.ScreenConstants
import dagger.hilt.android.AndroidEntryPoint
import second.brain.main_navigator.AppNavHost
import second.brain.ui.theme.SecondBrainTheme
import ui.composables.AppTopBar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var rootViewModel: RootViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            rootViewModel = hiltViewModel()
            lifecycle.addObserver(rootViewModel)

            SecondBrainTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {

                    }
                ) {
                    val bottomPadding = it.calculateBottomPadding()

                    Column(modifier = Modifier.fillMaxSize()) {

                        AppTopBar(
                            navController = navController,
                            logout = {
                                rootViewModel.logoutUser(){
                                    navController.popBackStack()
                                    navController.navigate(ScreenConstants.OnboardingParent)

                                }

                            }
                        )
                        Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                            AppNavHost(navController = navController, setUserActive = {
                                rootViewModel.setUserActive()
                            })
                        }
                    }
                }

            }
        }
    }
}