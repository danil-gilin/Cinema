package com.example.cinema.presenter.home.homepage

import javax.inject.Inject

class HomePageFactory@Inject constructor (private val homePageViewModel: HomePageViewModel) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return homePageViewModel as T
    }
}