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

    @Query("SELECT * FROM w504 WHERE id IN (SELECT id FROM state WHERE review <= :date)")
    suspend fun loadByReviewDate(date: Int?): List<QueryResult>

    @Query("SELECT w504.id, w504.word, w504.code, w504.pronunciation, w504.sample, w504.definition, w504.translate, state.review, state.number FROM w504, state WHERE w504.id = state.id AND w504.id IN (SELECT state.id FROM state WHERE review IS NOT null)")
    suspend fun loadAllLearnedWords(): List<QueryResult2>

    @Query("SELECT * FROM W504 WHERE id IN (SELECT id FROM state WHERE review IS null LIMIT 10)")
    suspend fun loadNewWords(): List<QueryResult>

    @Query("UPDATE state SET review = :date WHERE id == :id")
    suspend fun updateReviewDate(date: Int, id: Int)

    @Query("UPDATE state SET number = :number WHERE id == :id")
    suspend fun updateBoxNumber(number: Int, id: Int)

    @Query("UPDATE state SET review = null")
    suspend fun reset()
}