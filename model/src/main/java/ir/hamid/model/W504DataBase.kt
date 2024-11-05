package ir.hamid.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

@Database(entities = [W504::class, State::class], version = 1)
abstract class W504DataBase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: W504DataBase? = null

        fun getDatabase(context: Context): W504DataBase {
            return INSTANCE ?: synchronized(this) {
                val tempInstance = INSTANCE
                if (tempInstance != null) {
                    return tempInstance
                }

                val dbName = "W504.db"
                val dbPath = "database/$dbName"
                val dbFile = File(context.getDatabasePath(dbName).path)

                if (!dbFile.exists()) {
                    context.assets.open(dbPath).use { inputStream ->
                        dbFile.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    W504DataBase::class.java,
                    dbName
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}