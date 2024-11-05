package ir.hamid.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val counterKey = intPreferencesKey("counter")


    val counter: Flow<Int?> = dataStore.data.map { pref ->
        pref[counterKey] ?: 0
    }


    suspend fun saveCounter(value: Int) {
        dataStore.edit { pref ->
            pref[counterKey] = value
        }
    }
}