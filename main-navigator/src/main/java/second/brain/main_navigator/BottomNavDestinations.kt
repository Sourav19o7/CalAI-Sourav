package second.brain.main_navigator

import androidx.compose.runtime.Immutable
import constants.ScreenConstants
import second.brain.main_resources.R

@Immutable
enum class BottomNavDestinations(
    val screenName: String,
    val screenIcon: Int,
    val screenRoute: ScreenConstants
) {
    Dashboard(
        "Dashboard",
        R.drawable.bottom_dashboard,
        ScreenConstants.HomeScreen
    ),
    Task(
        "Tasks",
        R.drawable.bottom_tasks,
        ScreenConstants.UserTasks
    ),
    Add(
        "Add",
        R.drawable.bottom_add,
        ScreenConstants.AddTask
    ),
    Notification(
        "Notification",
        R.drawable.bottom_notification,
        ScreenConstants.UserTasks
    ),
    Search(
        "Search",
        R.drawable.bottom_search,
        ScreenConstants.UserTasks
    ),
}
