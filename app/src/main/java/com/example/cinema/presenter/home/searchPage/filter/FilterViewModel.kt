package com.example.cinema.presenter.home.searchPage.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.FilterLocalUseCase
import com.example.cinema.domain.GetFilterCountryAndGenre
import com.example.cinema.entity.filterEntity.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterViewModel @Inject constructor(
    private val filterLocalUseCase: FilterLocalUseCase,
    private val getFilterCountryAndGenre: GetFilterCountryAndGenre
) : ViewModel() {
    private val _filterChannel = Channel<Filter> { }
    val filterChannel = _filterChannel.receiveAsFlow()

    fun getFilter() {
        viewModelScope.launch {
            val filter = filterLocalUseCase.getFilterLocal()
            _filterChannel.send(filter)
        }
    }

    fun setFilter(type: TypeFilter, date: Any) {
        val filterTemp=filterLocalUseCase.getFilterLocal()
        viewModelScope.launch {
            when (type) {
                TypeFilter.TYPE -> filterTemp.type = date as TypeFilmFilter
                TypeFilter.COUNTRY -> filterTemp.country = date as String
                TypeFilter.GENRE -> filterTemp.genre = date as String
                TypeFilter.YEARFROM -> filterTemp.yearFrom = date as String
                TypeFilter.YEARTO -> filterTemp.yearTo = date as String
                TypeFilter.RATINGFROM -> filterTemp.ratingFrom = date as Int
                TypeFilter.RATINGTO -> filterTemp.ratingTo = date as Int
                TypeFilter.SORT -> filterTemp.sort = date as SortFilter
                TypeFilter.WATCH -> filterTemp.watch = date as Boolean
            }
            filterLocalUseCase.setFilterLocal(filterTemp)
            _filterChannel.send(filterTemp)
        }
    }
}