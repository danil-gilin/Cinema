package com.example.cinema.presenter.home.homepage.filmInfo.filmGallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetFilmGalleryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmGalleryViewModel @Inject constructor(private val getfilmFallery: GetFilmGalleryUseCase) : ViewModel() {
   private val _state= MutableStateFlow<StateFilmGallery>(StateFilmGallery.Loading)
    val state=_state.asStateFlow()

    fun getFilmGalery(idFilm: Int) {
        viewModelScope.launch {
            _state.value=StateFilmGallery.Loading
                _state.value=StateFilmGallery.Success(getfilmFallery.getFilmGallery(idFilm))
        }

    }
}