package ir.hamid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ir.hamid.model.QueryResult
import ir.hamid.model.QueryResult2
import ir.hamid.model.W504Repository
import kotlinx.coroutines.launch

class W504ViewModel(private val repository: W504Repository) : ViewModel() {


    private var _allWords = MutableLiveData<List<QueryResult>>()
    private var _words = MutableLiveData<List<QueryResult>>()
    private var _words2 = MutableLiveData<List<QueryResult2>>()

    val allWords: LiveData<List<QueryResult>> get() = _allWords
    val words: LiveData<List<QueryResult>> get() = _words
    val words2: LiveData<List<QueryResult2>> get() = _words2

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val data = repository.gelAll()
            _allWords.value = data
        }
    }

    fun fetchDataByDate(date: Int?) {
        viewModelScope.launch {
            val data = repository.loadByReviewDate(date)
            _words.value = data
        }
    }

    fun fetchData(event: String, str: String) {
        when (event) {
            "word" -> {
                viewModelScope.launch {
                    val data = repository.loadByWord(str)
                    _words.value = data
                }
            }

            "sample" -> {
                viewModelScope.launch {
                    val data = repository.loadBySample(str)
                    _words2.value = data
                }
            }

            "translate" -> {
                viewModelScope.launch {
                    val data = repository.loadByTranslate(str)
                    _words.value = data
                }
            }
        }
    }

}

class WordViewModelFactory(private val repository: W504Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(W504ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return W504ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}