// app/src/main/java/second/brain/RootViewModel.kt
package second.brain

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.DataPreferencesManager
import data.repository.DataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import second.brain.feature_post.domain.repository.PostsRepository
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val dataPreferencesManager: DataPreferencesManager,
    private val dataRepository: DataRepository,
    private val postsRepository: PostsRepository
) : ViewModel(), LifecycleEventObserver {

    fun logoutUser(userLoggedOut: () -> Unit) {
        viewModelScope.launch {
            try {

                dataPreferencesManager.isLoggedIn = false
                val currentUser = dataRepository.getData().first()
                val userId = currentUser.userId

                if (!userId.isNullOrEmpty()) {
                    postsRepository.setUserActiveStatus(userId, false)
                }

                dataPreferencesManager.clearUserData()

                userLoggedOut()
            } catch (e: Exception) {
                dataPreferencesManager.clearUserData()
            }
        }
    }

    fun setUserActive() {
        viewModelScope.launch {
            val currentUser = dataRepository.getData().first()
            val userId = currentUser.userId
            if (!userId.isNullOrEmpty()) {
                postsRepository.setUserActiveStatus(userId, true)
            }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
    }
}