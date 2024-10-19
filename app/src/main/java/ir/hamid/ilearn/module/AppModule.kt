package ir.hamid.ilearn.module

import android.content.Context
import androidx.datastore.core.DataStore

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.hamid.model.DataStoreRepository
import ir.hamid.model.W504DataBase
import ir.hamid.model.W504Repository
import ir.hamid.model.WordDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModelModule {


    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("settings") }
        )
    }

    @Provides
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepository(dataStore)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): W504DataBase {
        return W504DataBase.getDatabase(context)
    }

    @Provides
    fun provideDao(dataBase: W504DataBase): WordDao {
        return dataBase.wordDao()
    }

    @Provides
    fun provideRepository(dao: WordDao): W504Repository {
        return W504Repository(dao)
    }

}