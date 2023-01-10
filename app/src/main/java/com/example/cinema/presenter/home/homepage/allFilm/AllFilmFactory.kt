package com.example.cinema.presenter.home.homepage.allFilm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class AllFilmFactory @Inject constructor(private val allFilmViewModel: AllFilmViewModel) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return allFilmViewModel as T
    }
}