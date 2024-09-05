package ir.hamid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import ir.hamid.model.QueryResult
import ir.hamid.model.W504
import ir.hamid.model.W504Repository

class W504ViewModel(private val repository: W504Repository) : ViewModel() {

    private val _allWords: LiveData<List<QueryResult>> = repository.allWords.asLiveData()
    val allWords: LiveData<List<QueryResult>> get() = _allWords

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