package com.example.cinema.presenter.home.searchPage.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetFilterCountryAndGenre
import com.example.cinema.entity.filterEntity.CountryFilter
import com.example.cinema.entity.filterEntity.GenreFilter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenreViewModel @Inject constructor(private val getFilterCountryAndGenre: GetFilterCountryAndGenre) : ViewModel() {
    private val _listGenreChannel = Channel<List<GenreFilter>> { }
    val listGenreChannel = _listGenreChannel.receiveAsFlow()

    fun getGenreList(selectGenre: String) {
        viewModelScope.launch {
            var listGenre= getFilterCountryAndGenre.getFilterGenre()
            val id=listGenre.find { it.genre==selectGenre.lowercase() }?.id ?:0
            listGenre= listGenre.filter { it.genre!=selectGenre.lowercase()}
            val list= arrayListOf(GenreFilter(selectGenre,id))
            list.addAll(listGenre)
            _listGenreChannel.send(list)
        }
    }

    fun getGenreListSearch(countrySbstr: String) {
        viewModelScope.launch {
            var listGenre= getFilterCountryAndGenre.getFilterGenre()
            listGenre=listGenre.filter { it.genre.contains(countrySbstr,true) }
            _listGenreChannel.send(listGenre)
        }
    }
}