package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.filterEntity.CountryFilter
import com.example.cinema.entity.filterEntity.GenreFilter
import javax.inject.Inject

class FilterRepository @Inject constructor() {
    private var listCountry: List<CountryFilter>? = null
    private var listGenre: List<GenreFilter>? = null
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
}