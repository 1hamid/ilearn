package ir.hamid.model

class W504Repository(private val wordDao: WordDao) {

    suspend fun gelAll(): List<QueryResult> {
        return wordDao.getAll()
    }

    suspend fun loadByWord(str: String?): List<QueryResult> {
        return wordDao.loadByWord(str)
    }

    suspend fun loadBySample(str: String?): List<QueryResult2> {
        return wordDao.loadBySample(str)
    }

    suspend fun loadByTranslate(str: String?): List<QueryResult> {
        return wordDao.loadByTranslate(str)
    }

}