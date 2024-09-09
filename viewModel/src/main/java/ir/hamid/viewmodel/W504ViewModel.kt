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
    val allWords: LiveData<List<QueryResult>> get() = _allWords

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val data = repository.gelAll()
            _allWords.value = data
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