package com.example.cinema.presenter.home.searchPage.country

sealed class CountryState {
    object Loading : CountryState()
    data class Error(val error: String) : CountryState()
    object Success : CountryState()
}