package com.example.cinema.presenter.home.searchPage.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetFilterCountryAndGenre
import com.example.cinema.entity.filterEntity.CountryFilter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryViewModel @Inject constructor(private val getFilterCountryAndGenre: GetFilterCountryAndGenre): ViewModel() {

    private val _listCountryChannel = Channel<List<CountryFilter>> { }
    val listCountryChannel = _listCountryChannel.receiveAsFlow()

    private val _state= MutableStateFlow<CountryState>(CountryState.Loading)
    val state=_state.asStateFlow()

    fun getCountryList(selectCountry: String) {
        viewModelScope.launch {
            try {
            _state.value= CountryState.Loading
           var listCountry= getFilterCountryAndGenre.getFilterCountry()
            val id=listCountry.find { it.country==selectCountry }?.id ?:0
            listCountry= listCountry.filter { it.country!=selectCountry }
            val list= arrayListOf(CountryFilter(selectCountry,id))
            list.addAll(listCountry)
            _listCountryChannel.send(list)
            _state.value= CountryState.Success
            }catch (e:Throwable){
                _state.value= CountryState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }

   }

    fun getCountryListSearch(countrySbstr: String) {

        viewModelScope.launch {
            try {
                _state.value= CountryState.Loading
            var listCountry= getFilterCountryAndGenre.getFilterCountry()
            listCountry=listCountry.filter { it.country.contains(countrySbstr,true) }
            _listCountryChannel.send(listCountry)
            _state.value= CountryState.Success
            }catch (e:Throwable){
                _state.value= CountryState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }
}