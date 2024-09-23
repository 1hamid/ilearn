package ir.hamid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ir.hamid.model.QueryResult
import ir.hamid.model.W504Repository
import kotlinx.coroutines.launch

class W504ViewModel(private val repository: W504Repository) : ViewModel() {


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

    private fun fetchData() {
        viewModelScope.launch {
            val data = repository.gelAll()
            _allWords.value = data
        }
    }

    fun fetchDataByDate(date: Long?) {
        viewModelScope.launch {
            val data = repository.loadByReviewDate(date)
            if (date == null) {
                _newWords.value = data
            } else {
                _reviewWords.value = data
            }
        }
    }

    fun fetchData(event: String, str: String) {
        when (event) {
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