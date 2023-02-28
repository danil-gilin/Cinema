package com.example.cinema.presenter.home.searchPage.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class FilterFactory @Inject constructor  (private val filterViewModel: FilterViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return filterViewModel as T
    }
}