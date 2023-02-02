package com.example.cinema.presenter.home.homepage.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetCinemaGenreUseCase
import com.example.cinema.domain.GetGenreNameUseCase
import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinemaTop.CinemaTop
import com.example.cinema.entity.typeListFilm.TypeListFilm
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val getCinemaGenreUseCase: GetCinemaGenreUseCase,
    private val getGenreNameUseCase: GetGenreNameUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<HomePageState>(HomePageState.Loading)
    val state = _state.asStateFlow()

    private val _cinemaGenreChannel1 = Channel<Pair<TypeListFilm, AllCinema>> { }
    val cinemaGenreChannel1 = _cinemaGenreChannel1.receiveAsFlow()

    private val _cinemaGenreChannel2 = Channel<Pair<TypeListFilm, AllCinema>> { }
    val cinemaGenreChannel2 = _cinemaGenreChannel2.receiveAsFlow()

    private val _cinemaPremiers = Channel<Pair<TypeListFilm, AllCinema>> { }
    val cinemaPremiers = _cinemaPremiers.receiveAsFlow()

    private val _cinemaSerial = Channel<Pair<TypeListFilm, AllCinema>> { }
    val cinemaSerial = _cinemaSerial.receiveAsFlow()

    private val _cinemaPopularFilm1 = Channel<Pair<TypeListFilm, CinemaTop>> { }
    val cinemaPopularFilm1 = _cinemaPopularFilm1.receiveAsFlow()

    private val _cinemaPopularFilm2 = Channel<Pair<TypeListFilm, CinemaTop>> { }
    val cinemaPopularFilm2 = _cinemaPopularFilm2.receiveAsFlow()

    private val _test = Channel<String> { }
    val test = _test.receiveAsFlow()

    fun getCinema() {
        viewModelScope.launch {
            try {
                _state.value = HomePageState.Loading
                val genreCinema1 = getCinemaGenre(_cinemaGenreChannel1)
                val genreCinema2 = getCinemaGenre(_cinemaGenreChannel2)
                val premiers = getCinemaPremiers()
                val serial = getSerial()
                val popular = getPopularFilm("TOP_250_BEST_FILMS", "ТОП 250", _cinemaPopularFilm1)
                val popular1 = getPopularFilm("TOP_AWAIT_FILMS", "Топ ожидаемых", _cinemaPopularFilm2)
                _state.value = HomePageState.Success(genreCinema1,genreCinema2,premiers,serial,popular,popular1)
            } catch (e: Exception) {
                _state.value =
                    HomePageState.Error("Придумайте название \n для вашей новой коллекции |")
            }
        }
    }

    suspend fun getCinemaGenre(channel: Channel<Pair<TypeListFilm, AllCinema>>): Pair<TypeListFilm, AllCinema>? {
        val genreName = getGenreNameUseCase.getGenreName()
        var genre = 0
        var country = 0
        var allCinema: AllCinema? = null
        var page = 10
        var nameGenre = ""
        val typeListFilm = TypeListFilm(nameGenre)
        try {
            while (true) {
                when ((0..100).random()) {
                    in 0..60 -> {
                        genre = (0 until genreName.genres.size).random()
                        allCinema = getCinemaGenreUseCase.getCinemaGenre(genre + 1, 1)
                        page = allCinema.totalPages ?: 1
                        if (page == 0) {
                            page = 1
                        }
                        val tempPage = (1..page).random()
                        allCinema = getCinemaGenreUseCase.getCinemaGenre(genre + 1, tempPage)
                        nameGenre = genreName.genres[genre].genre
                        typeListFilm.name = nameGenre
                        typeListFilm.genreId = genre + 1
                    }
                    in 61..80 -> {
                        country = (0 until genreName.countries.size).random()
                        genre = (0 until genreName.genres.size).random()
                        allCinema = getCinemaGenreUseCase.getCinemaGenreWithCountry(
                            country + 1,
                            genre + 1,
                            1
                        )
                        nameGenre =
                            genreName.genres[genre].genre + " " + genreName.countries[country].country
                        page = allCinema.totalPages ?: 1
                        if (page == 0) {
                            page = 1
                        }
                        val tempPage = (1..page).random()
                        allCinema = getCinemaGenreUseCase.getCinemaGenreWithCountry(
                            country + 1,
                            genre + 1,
                            tempPage
                        )
                        typeListFilm.name = nameGenre
                        typeListFilm.genreId = genre + 1
                        typeListFilm.countryId = country + 1
                    }
                    in 81..100 -> {
                        country = (0 until genreName.countries.size).random()
                        allCinema = getCinemaGenreUseCase.getCountryCinema(country + 1, 1)
                        page = allCinema.totalPages ?: 1
                        if (page == 0) {
                            page = 1
                        }
                        val tempPage = (1..page).random()
                        allCinema =
                            getCinemaGenreUseCase.getCountryCinema(country + 1, tempPage)
                        nameGenre = genreName.countries[country].country
                        typeListFilm.name = nameGenre
                        typeListFilm.countryId = country + 1
                    }
                }
                if (allCinema?.items?.isNotEmpty() == true) {
                    if (allCinema.items.size > 20) {
                        return Pair(typeListFilm, allCinema)
                        // channel.send(Pair(typeListFilm, allCinema))
                    } else {
                        return Pair(typeListFilm, allCinema)
                        // channel.send(Pair(typeListFilm, allCinema))
                    }
                    break
                }
            }
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getCinemaPremiers(): Pair<TypeListFilm, AllCinema>? {
        try {
            val month = Calendar.getInstance()
                .getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)?.uppercase()
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val cinema = getCinemaGenreUseCase.getPremieresFilm(year, month!!)
            val typeListFilm = TypeListFilm("Премьеры", month = month, year = year)
            return Pair(typeListFilm, cinema)
            // _cinemaPremiers.send(Pair(typeListFilm, cinema))
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getPopularFilm(
        type: String,
        genreName: String,
        channel: Channel<Pair<TypeListFilm, CinemaTop>>
    ): Pair<TypeListFilm, CinemaTop>? {

        try {
            var page = 10
            while (true) {
                val cinema = getCinemaGenreUseCase.getPopularFilm(type, (1..page).random())
                if (cinema.films.isEmpty()) {
                    page = cinema.pagesCount
                    if (page == 0) {
                        page = 1
                    }
                } else {
                    val typeListFilm = TypeListFilm(genreName, topFilmType = type)
                    return Pair(typeListFilm, cinema)
                    //channel.send(Pair(typeListFilm, cinema))
                    break
                }
            }
        } catch (e: Exception) {
            return null
        }

    }

    private suspend fun getSerial(): Pair<TypeListFilm, AllCinema>? {
        try {
            var page = 10
            while (true) {
                val cinema = getCinemaGenreUseCase.getSerial((1..page).random())
                if (cinema.items.isEmpty()) {
                    page = cinema.totalPages ?: 1
                    if (page == 0) {
                        page = 1
                    }
                } else {
                    val typeListFilm = TypeListFilm("Сериалы", serial = true)
                    return Pair(typeListFilm, cinema)
                    // _cinemaSerial.send(Pair(typeListFilm, cinema))
                    break
                }
            }
        } catch (e: Exception) {
            return null
        }
    }
}