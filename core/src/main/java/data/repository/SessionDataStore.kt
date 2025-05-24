package data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import constants.DataPreferencesKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import data.helper.DataPreferences
import javax.inject.Inject
import javax.inject.Singleton


private val Context.preferencesDataStore by preferencesDataStore(
    name = "session_preference",
)

@Singleton
class DataPreferencesManager @Inject constructor(
    @ApplicationContext appContext: Context,
) {
    private val userPrefDataStore = appContext.preferencesDataStore

    var isLoggedIn by DataPreferences(
        dataStore = userPrefDataStore,
        key = DataPreferencesKeys.loggedIn,
        defaultValue = false
    )

    var userId by DataPreferences(
        dataStore = userPrefDataStore,
        key = DataPreferencesKeys.userId,
        defaultValue = ""
    )

    suspend fun clearUserData() {
        userPrefDataStore.edit {
            it.clear()
        }
    }
}
