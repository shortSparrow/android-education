package com.example.myfavoritemoviesmvvm.model.add_edit

import androidx.lifecycle.ViewModel
import com.example.myfavoritemoviesmvvm.model.movie.Movie

class AddEditViewModel : ViewModel() {
    var behavior = "create"
    var movie = Movie()
}