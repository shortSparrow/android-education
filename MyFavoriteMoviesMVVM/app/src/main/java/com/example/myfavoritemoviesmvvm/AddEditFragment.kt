package com.example.myfavoritemoviesmvvm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myfavoritemoviesmvvm.databinding.AddEditFragmentBinding
import com.example.myfavoritemoviesmvvm.model.add_edit.AddEditViewModel
import com.example.myfavoritemoviesmvvm.model.genre.Genre
import com.example.myfavoritemoviesmvvm.model.genre.GenreRepository
import com.example.myfavoritemoviesmvvm.model.genre.GenreViewModel
import com.example.myfavoritemoviesmvvm.model.genre.GenreViewModelFactory
import com.example.myfavoritemoviesmvvm.model.movie.MovieRepository
import com.example.myfavoritemoviesmvvm.model.movie.MovieViewModel
import com.example.myfavoritemoviesmvvm.model.movie.MovieViewModelFactory
import com.google.android.material.snackbar.Snackbar


class AddEditFragment : Fragment() {
    private var _binding: AddEditFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = AddEditFragment()
    }

    private val addEditViewModel: AddEditViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddEditFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val movieRepository = activity?.let { AppDatabase.getDatabase(it).movieDao }
            ?.let { MovieRepository(it) }
        val movieFactory = movieRepository?.let { MovieViewModelFactory(it) }
        val movieViewModel = movieFactory?.let {
            ViewModelProvider(
                this,
                it
            )
        }?.get(MovieViewModel::class.java)!!


        val genreRepository = activity?.let { AppDatabase.getDatabase(it).genreDao }
            ?.let { GenreRepository(it) }
        val genreFactory = genreRepository?.let { GenreViewModelFactory(it) }
        val genreViewModel = genreFactory?.let {
            ViewModelProvider(
                this,
                it
            )
        }?.get(GenreViewModel::class.java)

        genreViewModel?.getGenres()?.observe(viewLifecycleOwner) {
            showInSpinner(it)
        }

        binding.movie = addEditViewModel.movie

        binding.saveEditMovie.setOnClickListener {
            if (addEditViewModel.movie.title.isNotEmpty() && addEditViewModel.movie.description.isNotEmpty() && addEditViewModel.movie.genreId.isNotEmpty()) {
                movieViewModel.insert(addEditViewModel.movie)
                this.findNavController().popBackStack()
            } else {
                Snackbar.make(
                    binding.root, "Fill all fields",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()
            }
        }

        binding.cancelEditMovie.setOnClickListener {
            this.findNavController().popBackStack()
        }

        binding.genreEditSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                onSelectedItemSpinner(p0, p1, p2, p3)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun onSelectedItemSpinner(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        val selectedGenre: Genre = parent?.getItemAtPosition(position) as Genre
        addEditViewModel.movie.genreId = selectedGenre.id.toString()
    }

    private fun showInSpinner(genreList: List<Genre>) {
        val genreArrayAdapter: ArrayAdapter<Genre>? = activity?.let {
            ArrayAdapter<Genre>(
                it.applicationContext,
                R.layout.spinner_item,
                genreList
            )
        }

        genreArrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
        binding.genreEditSpinner.adapter = genreArrayAdapter


        if (addEditViewModel.movie.genreId.isNotEmpty()) {
            binding.genreEditSpinner.setSelection(addEditViewModel.movie.genreId.toInt() - 1)
        } else {
            binding.genreEditSpinner.setSelection(0)
        }
    }
}