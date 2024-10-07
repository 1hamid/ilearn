package ir.hamid.model

import javax.inject.Inject

class W504Repository @Inject constructor(private val wordDao: WordDao) {

    suspend fun gelAll(): List<QueryResult> {
        return wordDao.getAll()
    }

    suspend fun loadByWord(str: String): List<QueryResult> {
        return wordDao.loadByWord(str)
    }

    suspend fun loadBySample(str: String): List<QueryResult> {
        return wordDao.loadBySample(str)
    }

    suspend fun loadByTranslate(str: String): List<QueryResult> {
        return wordDao.loadByTranslate(str)
    }

    suspend fun loadByReviewDate(date: Int?): List<QueryResult> {
        return wordDao.loadByReviewDate(date)
    }

    suspend fun loadAllLearnedWords(): List<QueryResult> {
        return wordDao.loadAllLearnedWords()
    }

    suspend fun loadNewWords(): List<QueryResult> {
        return wordDao.loadNewWords()
    }

    suspend fun updateReviewDate(date: Int, id: Int) {
        return wordDao.updateReviewDate(date, id)
    }

    suspend fun reset() {
        return wordDao.reset()
    }
}