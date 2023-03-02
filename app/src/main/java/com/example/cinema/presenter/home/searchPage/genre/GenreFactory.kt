package com.example.cinema.presenter.home.searchPage.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class GenreFactory @Inject constructor(private val genreViewModel: GenreViewModel):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return genreViewModel as T
    }
}