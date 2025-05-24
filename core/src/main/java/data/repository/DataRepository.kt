package data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DataRepository @Inject constructor(
    private val protoDataStore: DataStore<User>
) {

    suspend fun updateData(user: User) {
        if(user.userId?.isEmpty() == false) {
            protoDataStore.updateData {
                user
            }
        }
    }

    fun getData(): Flow<User> {
        return protoDataStore.data.catch {
            Log.e("DataRepository", "Error: ${it.message}")
        }
    }


}
