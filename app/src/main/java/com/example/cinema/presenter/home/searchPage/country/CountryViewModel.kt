package com.example.cinema.presenter.home.searchPage.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetFilterCountryAndGenre
import com.example.cinema.entity.filterEntity.CountryFilter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryViewModel @Inject constructor(private val getFilterCountryAndGenre: GetFilterCountryAndGenre): ViewModel() {

    private val _listCountryChannel = Channel<List<CountryFilter>> { }
    val listCountryChannel = _listCountryChannel.receiveAsFlow()

    fun getCountryList(selectCountry: String) {
        viewModelScope.launch {
           var listCountry= getFilterCountryAndGenre.getFilterCountryAndGenre()
            val id=listCountry.find { it.country==selectCountry }?.id ?:0
            listCountry= listCountry.filter { it.country!=selectCountry }
            val list= arrayListOf(CountryFilter(selectCountry,id))
            list.addAll(listCountry)
            _listCountryChannel.send(list)
        }
   }

    fun getCountryListSearch(countrySbstr: String) {
        viewModelScope.launch {
            var listCountry= getFilterCountryAndGenre.getFilterCountryAndGenre()
            listCountry=listCountry.filter { it.country.contains(countrySbstr,true) }
            _listCountryChannel.send(listCountry)
        }
    }
}