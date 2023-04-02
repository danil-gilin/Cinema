package com.example.cinema.presenter.home.profilePage.allFilmProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class AllFilmProfileFactory @Inject constructor(private val allFilmProfileViewModel: AllFilmProfileViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return allFilmProfileViewModel as T
    }
}