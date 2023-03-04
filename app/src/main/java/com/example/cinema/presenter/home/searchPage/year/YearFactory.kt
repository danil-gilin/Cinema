package com.example.cinema.presenter.home.searchPage.year

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class YearFactory @Inject constructor(private val yearViewModel: YearViewModel) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return yearViewModel as T
    }
}