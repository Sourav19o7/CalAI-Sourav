package extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {

    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()

    val parentEntry = remember(this) {
        navController.getSafeBackStackEntry<Any>(navGraphRoute)
    }
    return parentEntry?.let {
        hiltViewModel(it)
    } ?: hiltViewModel()

}

fun <T> NavController.getSafeBackStackEntry(route: String): NavBackStackEntry? {
    return try {
        getBackStackEntry(route)
    } catch (_: Exception) {
        null
    }
}
