package com.example.cinema.presenter.home.searchPage.filter

sealed class FilterState {
    object Loading : FilterState()
    object Success : FilterState()
    data class Error(val error: String) : FilterState()
}