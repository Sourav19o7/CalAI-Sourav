package second.brain.main_navigator.ui

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import second.brain.main_navigator.BottomNavDestinations
import second.brain.main_resources.R

@Composable
fun CustomBottomBar(
    navController: NavController
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val bottomNavEntries = BottomNavDestinations.entries

    // Show the bottom bar only if the current destination
    // is included in the bottomNavEntries
    val showBottomBar = bottomNavEntries.any {
        it.screenName != "Add" &&
        currentDestination?.hierarchy.orEmpty().any { destination ->
            destination.route?.contains(it.screenRoute.toString()) == true
        }
    }

    if (showBottomBar) {
        NavigationBar(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(100.dp),
            containerColor = Color.Transparent
        ) {
            bottomNavEntries.forEach { screen ->
                // Determine if the current destination matches the screen's route
                val isSelected = currentDestination?.route?.contains(
                    screen.screenRoute.toString()
                ) == true

                Log.i(
                    "CustomBottomBar",
                    "isSelected: $isSelected" +
                            "\nScreen Name : ${
                                screen.screenName
                            }" +
                            "\nDestination : ${
                                currentDestination?.route
                            }"
                )



                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clickable {
                            navController.navigate(screen.screenRoute) {
                                // Pop up to the current route only if it's not the same as the selected one
                                currentDestination?.route?.let { route ->
                                    if (!route.contains(screen.screenName) && screen.name != "Add") {
                                        popUpTo(route) {
                                            inclusive = true
                                        }
                                    }
                                }
                                launchSingleTop = true // Prevent multiple copies of the same destination
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (screen.name == "Add") {
                        Icon(
                            painter = painterResource(id = screen.screenIcon),
                            contentDescription = screen.screenName,
                            modifier = Modifier
                                .size(55.dp)
                        )
                    } else {
                        Icon(
                            tint = if (isSelected) Color.White else Color.Gray,
                            painter = painterResource(id = screen.screenIcon),
                            contentDescription = screen.screenName,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }

                    val selectorSize by animateDpAsState(
                        targetValue = if (isSelected && screen.name != "Add") 20.dp else 0.dp,
                        label = "selector size animation",
                        animationSpec = tween(
                            durationMillis = 300,
                            delayMillis = 0
                        )
                    )

                    Image(
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .size(selectorSize),
                        painter = painterResource(R.drawable.bottom_nav_selected),
                        contentDescription = "Selected Bottom Nav Icon"
                    )

                }


            }
        }
    }
}
