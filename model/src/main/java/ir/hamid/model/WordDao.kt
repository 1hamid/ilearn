package ir.hamid.model

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT word, translate FROM w504")
    fun getAll(): Flow<List<QueryResult>>

    @Query("SELECT word, translate FROM w504 WHERE word LIKE :str")
    fun loadByWord(str: String?): Flow<List<QueryResult>>

    @Query("SELECT word, translate FROM w504 WHERE translate LIKE :str")
    fun loadByTranslate(str: String?): Flow<List<QueryResult>>

    @Query("SELECT word, sample FROM w504 WHERE sample LIKE :str")
    fun loadBySample(str: String?): Flow<List<QueryResult2>>
}