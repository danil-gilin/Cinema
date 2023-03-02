package com.example.cinema.presenter.home.searchPage.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class CountryFactory @Inject constructor(private val countryViewModel: CountryViewModel) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return countryViewModel as T
    }
}