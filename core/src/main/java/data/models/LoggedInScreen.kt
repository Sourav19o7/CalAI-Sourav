package data.models

import androidx.compose.runtime.Immutable
import constants.ScreenConstants


@Immutable
enum class LoggedInScreen(
    val screenRoute: ScreenConstants
) {
    PostList(
        ScreenConstants.PostListScreen
    ),
}