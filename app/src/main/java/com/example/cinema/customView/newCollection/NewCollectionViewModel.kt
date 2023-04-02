package com.example.cinema.customView.newCollection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.CollectionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewCollectionViewModel @Inject constructor(
    private val collectionUseCase: CollectionUseCase
): ViewModel() {
    private val _state = MutableStateFlow<StateNewCollection>(StateNewCollection.Loading)
    val state = _state.asStateFlow()


    fun addCollection(nameCollection: String) {
        try {
        viewModelScope.launch {
            _state.value = StateNewCollection.Loading
            val id=collectionUseCase.addCollection(nameCollection)
            _state.value = StateNewCollection.SuccessSaveCollection(id)
        }
        }catch (e:Exception){
            _state.value = StateNewCollection.Error(e.message.toString())
        }
    }
}