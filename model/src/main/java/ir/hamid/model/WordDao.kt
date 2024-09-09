package ir.hamid.model

import androidx.room.Dao
import androidx.room.Query
@Dao
interface WordDao {

    @Query("SELECT word, translate FROM w504")
    suspend fun getAll(): List<QueryResult>

    @Query("SELECT word, translate FROM w504 WHERE word LIKE :str")
    suspend fun loadByWord(str: String?): List<QueryResult>

    @Query("SELECT word, translate FROM w504 WHERE translate LIKE :str")
    suspend fun loadByTranslate(str: String?): List<QueryResult>

    @Query("SELECT word, sample FROM w504 WHERE sample LIKE :str")
    suspend fun loadBySample(str: String?): List<QueryResult2>
}