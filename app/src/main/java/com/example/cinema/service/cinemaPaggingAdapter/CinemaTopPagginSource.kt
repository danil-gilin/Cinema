package com.example.cinema.service.cinemaPaggingAdapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinema.data.CinemaRepository
import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.cinemaTop.Film
import com.example.cinema.entity.fullInfoActor.typeListFilm.TypeListFilm

class CinemaTopPagginSource(private val typeListFilm: TypeListFilm): PagingSource<Int, Film>() {
    private val repository: CinemaRepository = CinemaRepository()

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? =1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page=params.key?:1
        var apiFunction= repository.getPopularFilm(typeListFilm.topFilmType!!,page)

        return kotlin.runCatching {
            apiFunction
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.films,
                    prevKey = null,
                    nextKey = if (it.films.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    private companion object{
        private val FIRST_PAGE =1
    }

}