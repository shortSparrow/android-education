package com.example.myfavoritemoviesmvvm.model.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GenreViewModelFactory(private val repository: GenreRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GenreViewModel(repository) as T
    }
}