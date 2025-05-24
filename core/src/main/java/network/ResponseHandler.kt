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

    // Handles successful responses
    fun <T : Any> handleSuccess(
        data: T?
    ): Resource<T> {
        return if (data == null) {
            Resource.error("No data found", null)
        } else {
            // Return successful resource if data is valid
            Resource.success(data)
        }
    }

    // Handles exceptions during API calls
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

    // Logs API failures and sends failure email notifications
    private fun logApiFailure(
        errorMessage: String
    ) {
        scope.launch {
            Log.i("API Failure", errorMessage)
        }
    }

    // Builds error message for logging or email
    private fun buildErrorMessage(
        errorJwtToken: String,
        endPoint: String,
        functionName: String,
        email: String,
        errorMessage: String
    ): String {
        return """
            ${context.packageManager.getPackageInfo(context.packageName, 0).versionName}  API Failed.
            
            Error: $errorMessage
            Endpoint: $endPoint
            Jwt Token: $errorJwtToken
            Method: $functionName
            Email: $email
        """.trimIndent()
    }

    // Handles HTTP exceptions
    private fun <T : Any> handleHttpException(
        e: HttpException,
        errorJwtToken: String,
        endPoint: String,
        functionName: String
    ): Resource<T> {

        return Resource.error(getErrorMessage(e.code()), null)
    }

    // Returns error messages based on error codes
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

// Enum class for handling specific error codes
enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}
