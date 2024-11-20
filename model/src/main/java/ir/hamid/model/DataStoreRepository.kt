package ir.hamid.model

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val counterKey = intPreferencesKey("counter")
    private val darkThemeKey = booleanPreferencesKey("isDarkThemeKey")


    val counter: Flow<Int?> = dataStore.data.map { pref ->
        pref[counterKey] ?: 0
    }

    val isDarkTheme: Flow<Boolean?> = dataStore.data.map { pref ->
        pref[darkThemeKey]
    }

    suspend fun saveCounter(value: Int) {
        dataStore.edit { pref ->
            pref[counterKey] = value
        }
    }

    suspend fun saveThemeState(isDark: Boolean) {
        dataStore.edit { pref ->
            pref[darkThemeKey] = isDark
            Log.i("lottie", "save    ${pref[darkThemeKey]}")
        }
    }
}