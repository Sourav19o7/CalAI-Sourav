package network

import android.content.Context
import retrofit2.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

@Singleton
open class ResponseHandler @Inject constructor(
    private val context: Context,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    fun <T : Any> handleSuccess(
        data: T?
    ): Resource<T> {
        return if (data == null) {
            Resource.error("No data found", null)
        } else {
            Resource.success(data)
        }
    }

    fun <T : Any> handleException(
        e: Exception,
        errorJwtToken: String,
        endPoint: String,
        functionName: String
    ): Resource<T> {
        return when (e) {
            is HttpException -> {
                handleHttpException(e, errorJwtToken, endPoint, functionName)
            }

            is SocketTimeoutException -> {
                Resource.error(getErrorMessage(ErrorCodes.SocketTimeOut.code), null)
            }

            else -> {
                Resource.error(getErrorMessage(Int.MAX_VALUE), null)
            }
        }
    }

    private fun <T : Any> handleHttpException(
        e: HttpException,
        errorJwtToken: String,
        endPoint: String,
        functionName: String
    ): Resource<T> {

        return Resource.error(getErrorMessage(e.code()), null)
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            469 -> "User ID is empty"
            else -> {
                Log.d("ResponseHandler", "getErrorMessage: $code")
                "Something went wrong"
            }
        }
    }
}

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}
