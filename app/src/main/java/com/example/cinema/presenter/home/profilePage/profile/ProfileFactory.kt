package com.example.cinema.presenter.home.profilePage.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ProfileFactory @Inject constructor(private val profileViewModel: ProfileViewModel):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return profileViewModel as T
    }
}