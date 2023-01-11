package com.example.cinema.presenter.home.homepage.filmInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetFilmFullInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmInfoViewModel @Inject constructor(private val getFilmFullInfo: GetFilmFullInfo) : ViewModel() {
   private val _state= MutableStateFlow<FilmInfoState>(FilmInfoState.Loading)
    val state=_state.asStateFlow()


    fun getFilm(id: Int) {
        viewModelScope.launch {
            _state.value=FilmInfoState.Loading

                val film = getFilmFullInfo.getFilmInfo(id)
                _state.value = FilmInfoState.Success(film)


        }
    }
}