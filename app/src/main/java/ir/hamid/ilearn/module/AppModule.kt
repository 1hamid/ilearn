package ir.hamid.ilearn.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.hamid.model.W504DataBase
import ir.hamid.model.W504Repository
import ir.hamid.model.WordDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModelModule {


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