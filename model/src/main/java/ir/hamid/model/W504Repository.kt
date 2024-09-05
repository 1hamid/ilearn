package ir.hamid.model

import kotlinx.coroutines.flow.Flow

class W504Repository(private val wordDao: WordDao) {

    val allWords: Flow<List<QueryResult>> = wordDao.getAll()

    fun loadByWord(str: String?): Flow<List<QueryResult>> {
        return wordDao.loadByWord(str)
    }

    fun loadBySample(str: String?): Flow<List<QueryResult2>> {
        return wordDao.loadBySample(str)
    }

    fun loadByTranslate(str: String?): Flow<List<QueryResult>> {
        return wordDao.loadByTranslate(str)
    }

}