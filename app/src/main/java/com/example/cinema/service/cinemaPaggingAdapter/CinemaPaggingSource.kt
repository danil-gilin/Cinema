package com.example.cinema.service.cinemaPaggingAdapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinema.data.CinemaFullInfoRepostory
import com.example.cinema.data.CinemaRepository
import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.typeListFilm.TypeListFilm
import javax.inject.Inject

class CinemaPaggingSource(private val typeListFilm: TypeListFilm):PagingSource<Int,Cinema>() {
    private val repository: CinemaRepository = CinemaRepository()

    override fun getRefreshKey(state: PagingState<Int, Cinema>): Int? =1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cinema> {
        val page=params.key?:1
        var apiFunction= repository.getCinemaGenre(1,page)
        if (typeListFilm.genreId!=null && typeListFilm.countryId==null){
            apiFunction=repository.getCinemaGenre(typeListFilm.genreId!!,page)
        }
        if(typeListFilm.countryId!=null && typeListFilm.genreId==null){
            apiFunction=repository.getCinemaCountry(typeListFilm.countryId!!,page)
        }
        if(typeListFilm.genreId!=null && typeListFilm.countryId!=null){
            apiFunction=repository.getCinemaGenreWithCountry(typeListFilm.countryId!!,typeListFilm.genreId!!,page)
        }
        if (typeListFilm.serial!=null){
            apiFunction=repository.getSerial(page)
        }
        if (typeListFilm.month!=null && typeListFilm.year!=null){
            apiFunction=repository.getPremieresFilm(typeListFilm.year!!,typeListFilm.month!!)
            return kotlin.runCatching {
                apiFunction
            }.fold(
                onSuccess = {
                    LoadResult.Page(
                        data = it.items,
                        prevKey = null,
                        nextKey = if (it.items.isEmpty()) null else page + 1
                    )
                },
                onFailure = {
                    LoadResult.Error(it)
                }
            )
        }

       return kotlin.runCatching {
           apiFunction
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.items,
                    prevKey = null,
                    nextKey = if (it.items.isEmpty()) null else page + 1
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