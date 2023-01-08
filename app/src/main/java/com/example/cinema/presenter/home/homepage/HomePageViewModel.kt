package com.example.cinema.presenter.home.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetCinemaGenreUseCase
import com.example.cinema.domain.GetGenreNameUseCase
import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinema.Cinema
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val getCinemaGenreUseCase: GetCinemaGenreUseCase,
    private val getGenreNameUseCase: GetGenreNameUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<HomePageState>(HomePageState.Loading)
    val state = _state.asStateFlow()

    fun getCinemaGenre() {
        viewModelScope.launch {
            val genreName =getGenreNameUseCase.getGenreName()
            var genre=0
            var country=0
            var allCinema:AllCinema?=null
            var nameGenre=""
            try {
            while (true){
                when((0..100).random()){
                    in 0..60->{
                        genre=(0 until genreName.genres.size).random()
                        allCinema = getCinemaGenreUseCase.getCinemaGenre(genre+1)
                        nameGenre= genreName.genres[genre].genre
                    }
                   in 61..80->{
                        country=(0 until genreName.countries.size).random()
                         genre=(0 until genreName.genres.size).random()
                        allCinema = getCinemaGenreUseCase.getCinemaGenreWithCountry(country+1,genre+1)
                       nameGenre= genreName.genres[genre].genre+" "+genreName.countries[country].country
                    }
                  in 81..100->{
                        country=(0 until genreName.countries.size).random()
                        allCinema = getCinemaGenreUseCase.getCountryCinema(country+1)
                         nameGenre= genreName.countries[country].country
                    }
                }
                if (allCinema?.items?.isNotEmpty() == true){
                    if(allCinema.items.size>20){
                        _state.value = HomePageState.Success(allCinema.items.subList(0,19), nameGenre)
                    }else {
                        _state.value = HomePageState.Success(allCinema.items, nameGenre)
                    }
                    break
                }
            }
            } catch (e: Exception) {
                _state.value = HomePageState.Error("Ошибка получения данных")
            }
        }
    }
}