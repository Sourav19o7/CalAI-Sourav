package utils

import android.content.Context
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import data.repository.DataRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class JwtBuilder @Inject constructor(
    @ApplicationContext val context: Context,
    private val dataRepository: DataRepository
) {
    suspend fun createJwt(
        claims: Map<String, Any>,
    ): String {

        val key = Keys.hmacShaKeyFor("4F5gH6+JyR3jK+1U7Nv89ytbH2Q/oV1bB3p2XzXmzQI=".toByteArray())

        val jwtToken = Jwts.builder()


        val versionCode = try {
            context.packageManager
                .getPackageInfo(context.packageName, 0).longVersionCode
        } catch (_: PackageManager.NameNotFoundException) {
            0L
        } catch (_: Exception) {
            0L
        }

        val userId = dataRepository.getData().first().userId

        jwtToken.claims(
            mapOf("version_code" to versionCode,
                "user_id" to userId,
//                "device" to Build.DEVICE,
//                "model" to Build.MODEL,
//                "manufacturer" to Build.MANUFACTURER,
//                "brand" to Build.BRAND,
//                "product" to Build.PRODUCT,
//                "os_version" to Build.VERSION.SDK_INT
            )
        )

        for (claim in claims) {
            jwtToken.claim(claim.key, claim.value)
        }
        return try {
            "auth ${jwtToken.signWith(key).compact()}"
        } catch (_: Exception) {
            ""
        }
    }

}