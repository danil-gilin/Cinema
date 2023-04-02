package com.example.cinema.presenter.home.searchPage.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.FilterLocalUseCase
import com.example.cinema.domain.GetFilterCountryAndGenre
import com.example.cinema.entity.filterEntity.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterViewModel @Inject constructor(
    private val filterLocalUseCase: FilterLocalUseCase,
    private val getFilterCountryAndGenre: GetFilterCountryAndGenre
) : ViewModel() {
    private val _filterChannel = Channel<Filter> { }
    val filterChannel = _filterChannel.receiveAsFlow()

    private val _state = MutableStateFlow<FilterState>(FilterState.Loading)
    val state = _state.asStateFlow()

    fun getFilter() {
        try {
            viewModelScope.launch {
                _state.value = FilterState.Loading
                val filter = filterLocalUseCase.getFilterLocal()
                _filterChannel.send(filter)
                _state.value = FilterState.Success
            }
        }catch (e:Throwable){
           _state.value = FilterState.Error("Во время обработки запроса \nпроизошла ошибка")
        }
    }

    fun setFilter(type: TypeFilter, date: Any) {
        try {
        val filterTemp = filterLocalUseCase.getFilterLocal()
        viewModelScope.launch {
            _state.value = FilterState.Loading
            when (type) {
                TypeFilter.TYPE -> filterTemp.type = date as TypeFilmFilter
                TypeFilter.COUNTRY -> filterTemp.country = date as String
                TypeFilter.GENRE -> filterTemp.genre = date as String
                TypeFilter.YEARFROM -> filterTemp.yearFrom = date as String
                TypeFilter.YEARTO -> filterTemp.yearTo = date as String
                TypeFilter.RATINGFROM -> {
                    val rating= date as Pair<Int, Int>
                    filterTemp.ratingFrom = rating.first
                    filterTemp.ratingTo = rating.second
                }
                TypeFilter.RATINGTO -> {
                   val rating= date as Pair<Int, Int>
                    filterTemp.ratingFrom = rating.first
                    filterTemp.ratingTo = rating.second
                }
                TypeFilter.SORT -> filterTemp.sort = date as SortFilter
                TypeFilter.WATCH -> filterTemp.watch = date as Boolean
            }
            filterLocalUseCase.setFilterLocal(filterTemp)
            _filterChannel.send(filterTemp)
            _state.value = FilterState.Success
        }
        }catch (e:Throwable){
            _state.value = FilterState.Error("Во время обработки запроса \nпроизошла ошибка")
        }
    }
}