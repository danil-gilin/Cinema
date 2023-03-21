package com.example.cinema.presenter.home.homepage.bottomSheetFilm.newCollection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.CollectionUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewCollectionViewModel @Inject constructor(
    private val collectionUseCase: CollectionUseCase
): ViewModel() {
    fun addCollection(nameCollection: String) {
        viewModelScope.launch {
            collectionUseCase.addCollection(nameCollection)
        }
    }
}