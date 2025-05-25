package ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import data.models.LoggedInScreen
import ui.text.AppTypography
import second.brain.main_resources.R

@Composable
fun AppTopBar(
    navController: NavController,
    logout: () -> Unit,
    modifier: Modifier = Modifier
) {


    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val topNavEntries = LoggedInScreen.entries

    // Show the top bar only if the current destination
    // is included in the topNavEntries
    val showTopBar = topNavEntries.any {
                currentDestination?.hierarchy.orEmpty().any { destination ->
                    destination.route?.contains(it.screenRoute.toString()) == true
                }
    }
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .systemBarsPadding()
                .padding(top = 15.dp)
        ) {

            if (showTopBar) {
                Text(
                    color = colorResource(R.color.blue_1),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 20.dp)
                        .clickable {
                            logout()
                        },
                    text = "Log Out",
                    fontFamily = FontFamily(
                        Font(R.font.light)
                    ),
                    fontSize = 18.sp
                )
            }

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Wall",
                fontFamily = FontFamily(
                    Font(R.font.semibold)
                ),
                fontSize = 22.sp
            )

        }

        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Black.copy(0.07f)
        )
    }
}