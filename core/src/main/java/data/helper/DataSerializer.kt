package data.helper

import androidx.datastore.core.Serializer
import domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object DataSerializer : Serializer<User> {


    override val defaultValue: User
        get() = User()


    override suspend fun readFrom(input: InputStream): User {
        return try {
            val json = Json {
                ignoreUnknownKeys = true
            }
            json.decodeFromString(
                deserializer = User.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e: Exception){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend  fun writeTo(t: User, output: OutputStream) {
        withContext(Dispatchers.IO) {
            val json = Json {
                ignoreUnknownKeys = true
            }
            output.write(
                json.encodeToString(
                    serializer = User.serializer(),
                    value = t
                ).toByteArray()
            )
        }
    }
}

