package com.example.cinema.data.retrofit

import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinemaTop.CinemaTop
import com.example.cinema.entity.filmInfo.FilmInfo
import com.example.cinema.entity.gener.GenreName
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey="f1877a5c-f583-454d-96e7-5d4f5a242426"
const val baseUrl="https://kinopoiskapiunofficial.tech/api/v2.2/"

object CinemaRetrofitObject {
   private val retrofit= Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val cinemaApi: CinemaApi = retrofit.create(CinemaApi::class.java)
    val cinemaFullInfoApi: CinemaFullInfoApi = retrofit.create(CinemaFullInfoApi::class.java)
}

interface CinemaApi {
@GET("films?&order=RATING&type=ALL&ratingFrom=3&ratingTo=10&yearFrom=1000&yearTo=3000")
@Headers("X-API-KEY: $apiKey")
suspend fun getGenreWithCountryFilm(@Query("countries") countries:Int,@Query("genres") genres:Int,@Query("page") page:Int=1): AllCinema

@GET("films?&order=RATING&type=ALL&ratingFrom=3&ratingTo=10&yearFrom=1000&yearTo=3000")
@Headers("X-API-KEY: $apiKey")
suspend fun getGenreFilm(@Query("genres") genres:Int,@Query("page") page:Int=1): AllCinema

@GET("films?&order=RATING&type=ALL&ratingFrom=3&ratingTo=10&yearFrom=1000&yearTo=3000")
@Headers("X-API-KEY: $apiKey")
suspend fun getCountryFilm(@Query("countries") countries:Int,@Query("page") page:Int=1): AllCinema

@GET("films/filters")
@Headers("X-API-KEY: $apiKey")
suspend fun getGenreAndCountry(): GenreName

@GET("films/premieres")
@Headers("X-API-KEY: $apiKey")
suspend fun getPremieresFilm(@Query("year") year:Int,@Query("month") month:String): AllCinema

@GET("films/top")
@Headers("X-API-KEY: $apiKey")
suspend fun getPopularFilm(@Query("type") year:String="TOP_AWAIT_FILMS",@Query("page") page:Int=1): CinemaTop

@GET("films?order=RATING&type=TV_SERIES&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000")
@Headers("X-API-KEY: $apiKey")
suspend fun getSerial(@Query("page") page:Int=1): AllCinema

}

interface CinemaFullInfoApi{
    @GET("films/{filmId}")
    @Headers("X-API-KEY: $apiKey")
    suspend fun getFullInfo(@Path("filmId") filmId:Int): FilmInfo
}