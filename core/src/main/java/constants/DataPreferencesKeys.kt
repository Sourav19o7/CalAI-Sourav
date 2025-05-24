package constants

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataPreferencesKeys {

    // Navigation Preferences
    val loggedIn = booleanPreferencesKey(PreferenceKeys.LOGGED_IN)

    // User Data
    val userId = stringPreferencesKey(PreferenceKeys.USER_ID)
}

object PreferenceKeys {
    const val LOGGED_IN = "LOGGED_IN"
    const val USER_ID = "USER_ID"
}