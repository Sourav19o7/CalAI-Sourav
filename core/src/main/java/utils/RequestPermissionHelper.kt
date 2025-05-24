package utils


import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

/**
 * A helper class for handling runtime permissions in Jetpack Compose.
 *
 * @param permissions An array of permissions that the app requires.
 */
class RuntimePermissionHelper(
    private val permissions: List<String>,
    private val launcher: ActivityResultLauncher<Array<String>>,
) {


    /**
     * Function to check if all the permissions are granted.
     * @param context The context.
     * @return True if all the permissions are granted, false otherwise.
     */
    fun arePermissionsGranted(context: Context): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    /**
     * Requests the runtime permissions.
     */
    fun requestPermissions(
    ) {
        //Adding try catch in case launcher gets used in a screen where it's not initialised
        try {
            launcher.launch(permissions.toTypedArray())
        } catch (_: Exception) {

        } catch (_: IllegalStateException) {

        }
    }

    /**
     * Function returns true if should show rationale
     */
    fun shouldShowRationale(context: Context): Boolean {
        return shouldShowRequestPermissionRationale(context, permissions)
    }
}

/**
 * A composable function to handle runtime permissions using the PermissionHelper.
 *
 * @param permissions An array of permissions that the app requires.
 * @return An instance of PermissionHelper.
 */
@Composable
fun rememberPermissionHelper(
    permissions: List<String>,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onShouldShowRationale: () -> Unit,
): RuntimePermissionHelper {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->

        if (
            result.all {
                it.value
            }
        ) {
            onPermissionGranted()
        } else {

            if (shouldShowRequestPermissionRationale(context, permissions)) {
                Log.d("PermissionHelper", "Should show rationale")
                onShouldShowRationale()
            } else {

                onPermissionDenied()
            }
        }
    }

    return remember { RuntimePermissionHelper(permissions, launcher) }
}

/**
 * Checks if we should show a rationale for requesting permissions.
 *
 * @param context The context.
 * @param permissions The requested permissions.
 * @return True if we should show a rationale, false otherwise.
 */
private fun shouldShowRequestPermissionRationale(
    context: Context,
    permissions: List<String>,
): Boolean {
    for (permission in permissions) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)) {
            return true
        }
    }
    return false
}