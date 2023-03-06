package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.filterEntity.*
import javax.inject.Inject

class FilterRepository @Inject constructor() {
    private var listCountry: List<CountryFilter>? = null
    private var listGenre: List<GenreFilter>? = null
    private var filterLocal= filter


    suspend fun getFilterCountry(): List<CountryFilter> {
        return when {
            getFilterCountryLocal() != null -> getFilterCountryLocal()!!
            getFilterCountryRetrofit() != null -> getFilterCountryRetrofit()!!
            else -> emptyList()
        }
    }

    suspend fun getFilterGenre(): List<GenreFilter> {
        return when {
            getFilterGenreLocal() != null -> getFilterGenreLocal()!!
            getFilterGenreRetrofit() != null -> getFilterGenreRetrofit()!!
            else -> emptyList()
        }
    }

   private suspend fun getFilterCountryRetrofit(): List<CountryFilter>? {
       listCountry=CinemaRetrofitObject.filterApi.getCountryAndGenreFilter().countries.filter { it.country!="" }
       listGenre=CinemaRetrofitObject.filterApi.getCountryAndGenreFilter().genres.filter { it.genre!="" }
        return listCountry
    }

  private  fun getFilterCountryLocal(): List<CountryFilter>? {
        return listCountry
    }


    private suspend fun getFilterGenreRetrofit(): List<GenreFilter>? {
        listCountry=CinemaRetrofitObject.filterApi.getCountryAndGenreFilter().countries.filter { it.country!="" }
        listGenre=CinemaRetrofitObject.filterApi.getCountryAndGenreFilter().genres.filter { it.genre!="" }
        return listGenre
    }

    private  fun getFilterGenreLocal(): List<GenreFilter>? {
        return listGenre
    }

    fun getFilterLocal(): Filter {
        return filterLocal
    }

    fun setFilterLocal(filter: Filter) {
        filterLocal=filter
    }

    companion object {
        val filter= Filter(
            type = TypeFilmFilter.ALL,
            country = "Россия",
            genre = "Комедия",
            yearFrom = "2000",
            yearTo = "2017",
            ratingFrom = 5,
            ratingTo = 10,
            sort = SortFilter.RATING,
            watch = false
        )
    }
}