package second.brain

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import data.helper.DataPreferences
import data.repository.DataPreferencesManager
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RootViewModel @Inject constructor(
    private val dataPreferencesManager: DataPreferencesManager
) : ViewModel(), LifecycleEventObserver {

    fun logoutUser() {
        viewModelScope.launch {
            dataPreferencesManager.clearUserData()
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

    }
}
