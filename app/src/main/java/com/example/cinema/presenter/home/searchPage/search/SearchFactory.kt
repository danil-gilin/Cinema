package com.example.cinema.presenter.home.searchPage.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SearchFactory @Inject constructor(private val searchViewModel: SearchViewModel):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return searchViewModel as T
    }
}