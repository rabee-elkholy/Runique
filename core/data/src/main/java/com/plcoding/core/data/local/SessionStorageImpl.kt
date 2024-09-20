package com.plcoding.core.data.local

import android.content.SharedPreferences
import com.plcoding.core.data.dto.AuthInfoDto
import com.plcoding.core.data.mapper.toAuthInfo
import com.plcoding.core.data.mapper.toAuthInfoDto
import com.plcoding.core.domain.session.AuthInfo
import com.plcoding.core.domain.session.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * SessionStorageImpl is an implementation of the [SessionStorage] interface that provides
 * secure storage of authentication information using [SharedPreferences].
 *
 * @property sharedPreferences The [SharedPreferences] instance used to store and retrieve auth info.
 */
class SessionStorageImpl(
    private val sharedPreferences: SharedPreferences,
) : SessionStorage {

    /**
     * Retrieves the authentication information from the encrypted session storage.
     * This function is performed on the IO dispatcher for background processing.
     *
     * @return The [AuthInfo] object or null if no information is found.
     */
    override suspend fun get(): AuthInfo? = withContext(Dispatchers.IO) {
        val authInfoJson = sharedPreferences.getString(KEY_AUTH_INFO, null)
        authInfoJson?.let { Json.decodeFromString<AuthInfoDto>(it).toAuthInfo() }
    }

    /**
     * Stores the given [AuthInfo] object into the encrypted session storage.
     * This function is performed on the IO dispatcher for background processing.
     *
     * @param authInfo The [AuthInfo] object containing the authentication details to be saved.
     */
    override suspend fun set(authInfo: AuthInfo) {
        withContext(Dispatchers.IO) {
            val authInfoJson = Json.encodeToString(authInfo.toAuthInfoDto())
            sharedPreferences.edit().putString(KEY_AUTH_INFO, authInfoJson).apply()
        }
    }

    /**
     * Clears the authentication information stored in the encrypted session storage.
     */
    override suspend fun clear() {
        sharedPreferences.edit().remove(KEY_AUTH_INFO).apply()
    }

    companion object {
        private const val KEY_AUTH_INFO = "key_auth_info"
    }
}
