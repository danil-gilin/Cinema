package com.example.cinema.presenter.home.homepage.bottomSheetFilm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class AddCollectionFactory @Inject constructor(private val addCollectionViewModel: AddCollectionViewModel) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return addCollectionViewModel as T
    }
}