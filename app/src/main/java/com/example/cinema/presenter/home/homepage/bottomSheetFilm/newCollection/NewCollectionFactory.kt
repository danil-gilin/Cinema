package com.example.cinema.presenter.home.homepage.bottomSheetFilm.newCollection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class NewCollectionFactory @Inject constructor(private val newCollectionViewModel: NewCollectionViewModel):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return newCollectionViewModel as T
    }
}