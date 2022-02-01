package com.example.myfavoritemoviesmvvm.model.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GenreViewModel(private val repository: GenreRepository) : ViewModel() {
    lateinit var genresList: LiveData<List<Genre>>


     fun insert(genre: Genre) =  viewModelScope.launch {
        repository.insert(genre)
    }

     fun update(genre: Genre) {
        viewModelScope.launch {
            repository.update(genre)
        }
    }

     fun delete(genre: Genre) {
        viewModelScope.launch {
            repository.delete(genre)
        }
    }

     fun getGenres(): LiveData<List<Genre>> {
        genresList = repository.getGenres()
        return repository.getGenres()
    }


}