package ir.hamid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hamid.model.DataStoreRepository
import ir.hamid.model.QueryResult
import ir.hamid.model.QueryResult2
import ir.hamid.model.W504Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class W504ViewModel @Inject constructor(
    private val w504repository: W504Repository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {


//    private var _allWords = MutableLiveData<List<QueryResult>>()
    private var _newWords = MutableLiveData<List<QueryResult>>()
    private var _learnedWords = MutableLiveData<List<QueryResult>>()
    private var _reviewWords = MutableLiveData<List<QueryResult>>()
    private var _searchWords = MutableLiveData<List<QueryResult>>()

//    val allWords: LiveData<List<QueryResult>> get() = _allWords
    val newWords: LiveData<List<QueryResult>> get() = _newWords
    val reviewWords: LiveData<List<QueryResult>> get() = _reviewWords
    val learnedWords: LiveData<List<QueryResult>> get() = _learnedWords
    val searchedWords: LiveData<List<QueryResult>> get() = _searchWords

    val counterData: LiveData<Int?> = dataStoreRepository.counter.asLiveData()
    val isDarkTheme: LiveData<Boolean?> = dataStoreRepository.isDarkTheme.asLiveData()

    fun saveCounter(value: Int) {
        viewModelScope.launch {
            dataStoreRepository.saveCounter(value)
        }
    }

    fun saveThemeState(isDark: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.saveThemeState(isDark)
        }
    }

//    private fun fetchAllWords() {
//        viewModelScope.launch {
//            val data = w504repository.gelAll()
//            _allWords.value = data
//        }
//    }

    fun fetchWordsByDate(date: Int?) {
        viewModelScope.launch {
            val data = w504repository.loadByReviewDate(date)
            _reviewWords.value = data
        }
    }

    fun fetchNewWords() {
        viewModelScope.launch {
            val data = w504repository.loadNewWords()
            _newWords.value = data
        }
    }

//    fun fetchLearnedWords() {
//        viewModelScope.launch {
//            val data = w504repository.loadAllLearnedWords()
//            _learnedWords.value = data
//        }
//    }

    fun fetchLearnedWordsByBox(box: Int) {
        viewModelScope.launch {
            val data = w504repository.loadLearnedWordsByBox(box)
            _learnedWords.value = data
        }
    }

    fun fetchWordsBySearch(searchType: String, str: String) {
        when (searchType) {
            "word" -> {
                viewModelScope.launch {
                    val data = w504repository.loadByWord(str)
                    _searchWords.value = data
                }
            }

            "sample" -> {
                viewModelScope.launch {
                    val data = w504repository.loadBySample(str)
                    _searchWords.value = data
                }
            }

            "translate" -> {
                viewModelScope.launch {
                    val data = w504repository.loadByTranslate(str)
                    _searchWords.value = data
                }
            }
        }
    }

    fun updateReviewDate(date: Int, boxNumber: Int, id: Int) {
        viewModelScope.launch {
            w504repository.updateReviewDate(date, id)
            w504repository.updateBoxNumber(boxNumber, id)
        }
    }

    fun reset() {
        viewModelScope.launch {
            w504repository.reset()
            saveCounter(0)
        }
    }

}

class WordViewModelFactory @Inject constructor(private val w504repository: W504Repository, private val dataStoreRepository: DataStoreRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(W504ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return W504ViewModel(w504repository, dataStoreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}