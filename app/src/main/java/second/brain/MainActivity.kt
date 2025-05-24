package second.brain

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import second.brain.main_navigator.AppNavHost
import second.brain.ui.theme.SecondBrainTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecondBrainTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {

                    }
                ) {
                    val bottomPadding = it.calculateBottomPadding()

                    Box(modifier = Modifier.fillMaxSize()) {

                        Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                            AppNavHost(navController = navController)
                        }
                    }
                }

            }
        }
    }
}