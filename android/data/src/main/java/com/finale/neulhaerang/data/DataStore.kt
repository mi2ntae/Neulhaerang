import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// At the top level of your kotlin file:
// Preferences Datastore 만들기
// kotlin 파일의 최상위 수준에서 인스턴트를 한 번 호출한 후 애플리케이션의 나머지 부분에서는 이 속성을 통해 인스턴트에 액세스
// DataStore를 싱글톤으로 유지
// name 매개변수는 Preferences Datastore의 이름
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

class DataStoreModule(
    private val context: Context
) {
//    private val Context.tokenDataStore by preferencesDataStore(TOKEN_DATASTORE)
//    private val Context.loginCheckDataStore by preferencesDataStore(LOGIN_CHECK_DATASTORE)

    private val _accessToken = stringPreferencesKey("access_token") // string 저장 키값
    private val _refreshToken = stringPreferencesKey("refresh_token") // int 저장 키값
    private val _deviceToken = stringPreferencesKey("deviceToken") // int 저장 키값
    private val _loginStatus = booleanPreferencesKey("login_status") // int 저장 키값

    //DataStore에서 값 읽기
    suspend fun getToken(): Flow<List<String>> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs.asMap().values.toList().map {
                    it.toString()
                }
            }
    }

    val accessTokenFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[_accessToken] ?: ""
        }

    suspend fun getAccessToken(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[_accessToken] ?: ""
            }
    }

    suspend fun getRefreshToken(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[_refreshToken] ?: ""
            }
    }

    suspend fun getDeviceToken(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[_deviceToken] ?: ""
            }
    }

    suspend fun getLoginStatus(): Flow<Boolean> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[_loginStatus] ?: false
            }
    }

    //DataStore에서 값 저장
    suspend fun saveToken(token: List<String>) {
        context.dataStore.edit { prefs ->
            prefs[_accessToken] = token.first()
            prefs[_refreshToken] = token.last()
        }
        // AccessToken, RefreshToken 이 제대로 들어온 여부를 확인하는 boolean 값
        context.dataStore.edit { prefs ->
            prefs[_loginStatus] = true
        }
    }

    suspend fun saveToken(key: String, value: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    suspend fun setAccessToken(value: String) {
        context.dataStore.edit { preferences ->
            preferences[_accessToken] = value
        }
    }

    suspend fun setRefreshToken(value: String) {
        context.dataStore.edit { preferences ->
            preferences[_refreshToken] = value
        }
    }

    suspend fun setDeviceToken(value: String) {
        context.dataStore.edit { preferences ->
            preferences[_deviceToken] = value
        }
    }

    suspend fun setLoginStatus(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[_loginStatus] = value
        }
    }


    suspend fun clearDataStore() {
        context.dataStore.edit {
            it.remove(_accessToken)
            it.remove(_refreshToken)
            it.remove(_loginStatus)
        }
    }
}