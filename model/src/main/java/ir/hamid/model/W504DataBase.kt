package ir.hamid.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

@Database(entities = [W504::class], version = 2)
abstract class W504DataBase : RoomDatabase() {

    //    abstract fun w504Repository(): W504Repository
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

                // Copy the prepopulated database
                val dbName = "W504.db"
                val dbPath = "database/$dbName"
                val dbFile = File(context.getDatabasePath(dbName).path)
                context.assets.open(dbPath).use { inputStream ->
                    dbFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }

                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        W504DataBase::class.java,
                        dbName
                    )
                        .createFromAsset(dbPath) // Path where the db is copied
                        .build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
}