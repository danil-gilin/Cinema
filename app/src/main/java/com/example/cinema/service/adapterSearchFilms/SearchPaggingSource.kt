package com.example.cinema.service.adapterSearchFilms

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinema.data.SearchRepository
import com.example.cinema.domain.GetSearchFilms
import com.example.cinema.entity.filterEntity.FilterSearch
import com.example.cinema.entity.searchFilm.SearchItem

class SearchPaggingSource(
    private val query: FilterSearch,
    private val repository: GetSearchFilms
) : PagingSource<Int, SearchItem>() {
    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            repository.getSerachFilmsPagging(query, page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }


}