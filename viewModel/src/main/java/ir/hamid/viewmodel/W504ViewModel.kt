package ir.hamid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hamid.model.QueryResult
import ir.hamid.model.W504Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class W504ViewModel @Inject constructor(private val repository: W504Repository) : ViewModel() {


    private var _allWords = MutableLiveData<List<QueryResult>>()
    private var _newWords = MutableLiveData<List<QueryResult>>()
    private var _reviewWords = MutableLiveData<List<QueryResult>>()
    private var _searchWords = MutableLiveData<List<QueryResult>>()

    val allWords: LiveData<List<QueryResult>> get() = _allWords
    val newWords: LiveData<List<QueryResult>> get() = _newWords
    val reviewWords: LiveData<List<QueryResult>> get() = _reviewWords

//    init {
//        fetchData()
//    }

    private fun fetchAllWords() {
        viewModelScope.launch {
            val data = repository.gelAll()
            _allWords.value = data
        }
    }

    fun fetchWordsByDate(date: Long?) {
        viewModelScope.launch {
            val data = repository.loadByReviewDate(date)
            _reviewWords.value = data
        }
    }

    fun fetchNewWords() {
        viewModelScope.launch {
            val data = repository.loadNewWords()
            _newWords.value = data
        }
    }

    fun fetchWordsBySearch(searchType: String, str: String) {
        when (searchType) {
            "word" -> {
                viewModelScope.launch {
                    val data = repository.loadByWord(str)
                    _searchWords.value = data
                }
            }

            "sample" -> {
                viewModelScope.launch {
                    val data = repository.loadBySample(str)
                    _searchWords.value = data
                }
            }

            "translate" -> {
                viewModelScope.launch {
                    val data = repository.loadByTranslate(str)
                    _searchWords.value = data
                }
            }
        }
    }

    fun updateReviewDate(date: Long, id: Int) {
        viewModelScope.launch {
            repository.updateReviewDate(date, id)
        }
    }

    fun reset() {
        viewModelScope.launch {
            repository.reset()
        }
    }

}

class WordViewModelFactory @Inject constructor(private val repository: W504Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(W504ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return W504ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}