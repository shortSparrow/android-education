package com.example.contactsmvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContactViewModelFactory (private val repository: ContactRepositoryImplementation ):
     ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
         return ContactViewModel(repository) as T
    }
}