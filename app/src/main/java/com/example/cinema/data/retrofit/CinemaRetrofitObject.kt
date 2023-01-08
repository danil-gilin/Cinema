package com.example.cinema.data.retrofit

import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.gener.GenreName
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

const val apiKey="a16112fd-a6d3-480c-a1bc-7ece0ea36df5"
const val baseUrl="https://kinopoiskapiunofficial.tech/api/v2.2/"

object CinemaRetrofitObject {
   private val retrofit= Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val cinemaApi: CinemaApi = retrofit.create(CinemaApi::class.java)
}

interface CinemaApi {
@GET("films?&order=RATING&type=ALL&ratingFrom=5&ratingTo=10&yearFrom=1000&yearTo=3000&page=1")
@Headers("X-API-KEY: $apiKey")
suspend fun getGenreWithCountryFilm(@Query("countries") countries:Int,@Query("genres") genres:Int): AllCinema

@GET("films?&order=RATING&type=ALL&ratingFrom=5&ratingTo=10&yearFrom=1000&yearTo=3000&page=1")
@Headers("X-API-KEY: $apiKey")
suspend fun getGenreFilm(@Query("genres") genres:Int): AllCinema

@GET("films?&order=RATING&type=ALL&ratingFrom=5&ratingTo=10&yearFrom=1000&yearTo=3000&page=1")
@Headers("X-API-KEY: $apiKey")
suspend fun getCountryFilm(@Query("countries") countries:Int): AllCinema


@GET("films/filters")
@Headers("X-API-KEY: $apiKey")
suspend fun getGenreAndCountry(): GenreName

}