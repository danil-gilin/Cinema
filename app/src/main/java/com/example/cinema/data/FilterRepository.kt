package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.filterEntity.CountryFilter
import javax.inject.Inject

class FilterRepository @Inject constructor() {
    private var listCountry: List<CountryFilter>? = null
    suspend fun getFilterCountryAndGenre(): List<CountryFilter> {
        return when {
            getFilterCountryAndGenreLocal() != null -> getFilterCountryAndGenreLocal()!!
            getFilterCountryAndGenreRetrofit() != null -> getFilterCountryAndGenreRetrofit()!!
            else -> emptyList()
        }
    }

   private suspend fun getFilterCountryAndGenreRetrofit(): List<CountryFilter>? {
       listCountry=CinemaRetrofitObject.filterApi.getCountryAndGenreFilter().countries.filter { it.country!="" }
        return listCountry
    }

  private  fun getFilterCountryAndGenreLocal(): List<CountryFilter>? {
        return listCountry
    }
}