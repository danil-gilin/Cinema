package com.example.cinema.presenter.home.searchPage.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetFilterCountryAndGenre
import com.example.cinema.entity.filterEntity.CountryFilter
import com.example.cinema.entity.filterEntity.GenreFilter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenreViewModel @Inject constructor(private val getFilterCountryAndGenre: GetFilterCountryAndGenre) : ViewModel() {
    private val _listGenreChannel = Channel<List<GenreFilter>> { }
    val listGenreChannel = _listGenreChannel.receiveAsFlow()

    private val _state= MutableStateFlow<GenreState>(GenreState.Loading)
    val state=_state.asStateFlow()

    fun getGenreList(selectGenre: String) {
        try {
        viewModelScope.launch {
            _state.value=GenreState.Loading
            var listGenre= getFilterCountryAndGenre.getFilterGenre()
            val id=listGenre.find { it.genre==selectGenre.lowercase() }?.id ?:0
            listGenre= listGenre.filter { it.genre!=selectGenre.lowercase()}
            val list= arrayListOf(GenreFilter(selectGenre,id))
            list.addAll(listGenre)
            _listGenreChannel.send(list)
            _state.value=GenreState.Success
        }
        }catch (e:Throwable){
            _state.value=GenreState.Error("Во время обработки запроса \nпроизошла ошибка")
        }
    }

    fun getGenreListSearch(countrySbstr: String) {
        try {
        viewModelScope.launch {
            _state.value=GenreState.Loading
            var listGenre= getFilterCountryAndGenre.getFilterGenre()
            listGenre=listGenre.filter { it.genre.contains(countrySbstr,true) }
            _listGenreChannel.send(listGenre)
            _state.value=GenreState.Success
        }
        }catch (e:Throwable){
            _state.value=GenreState.Error("Во время обработки запроса \nпроизошла ошибка")
        }
    }
}