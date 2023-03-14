package com.example.cinema.presenter.home.searchPage.search

import androidx.paging.PagingData
import com.example.cinema.entity.searchFilm.SearchItem
import kotlinx.coroutines.flow.Flow

sealed class SearchState{
    data class Success(val flowList: Flow<PagingData<SearchItem>>):SearchState()
    data class Error(val error:String):SearchState()
    object Loading:SearchState()
    object Empty:SearchState()
}
