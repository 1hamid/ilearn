package ir.hamid.model

import androidx.room.Dao
import androidx.room.Query
@Dao
interface WordDao {

    @Query("SELECT * FROM w504")
    suspend fun getAll(): List<QueryResult>

    @Query("SELECT * FROM w504 WHERE word LIKE '%' || :str || '%'")
    suspend fun loadByWord(str: String): List<QueryResult>

    @Query("SELECT * FROM w504 WHERE translate LIKE '%' || :str || '%'")
    suspend fun loadByTranslate(str: String): List<QueryResult>

    @Query("SELECT * FROM w504 WHERE sample LIKE '%' || :str || '%'")
    suspend fun loadBySample(str: String): List<QueryResult>

    @Query("SELECT * FROM w504 WHERE review <= :date")
    suspend fun loadByReviewDate(date: Long?): List<QueryResult>

    @Query("SELECT * FROM W504 WHERE review IS null LIMIT 10")
    suspend fun loadNewWords(): List<QueryResult>

    @Query("UPDATE w504 SET review = :date WHERE id == :id")
    suspend fun updateReviewDate(date: Long, id: Int)

    @Query("UPDATE w504 SET review = null")
    suspend fun reset()
}