package com.example.cinema.presenter.home.homepage.filmInfo.filmSerialInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetSerialSeasonUseCase
import com.example.cinema.presenter.home.homepage.allFilm.AllFilmState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmSerialInfoViewModel @Inject constructor(private val getSerialSeasonUseCase: GetSerialSeasonUseCase) : ViewModel() {
    private val _state=MutableStateFlow<SerialInfoState>(SerialInfoState.Loading)
    val state=_state.asStateFlow()


    fun getSerialInfo(idSerial: Int) {
        try {
            viewModelScope.launch {
                _state.value = SerialInfoState.Loading
                val info = getSerialSeasonUseCase.getSerials(idSerial)
                _state.value = SerialInfoState.Success(info)
            }
        }catch (e:Throwable){
            _state.value=SerialInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
        }
    }

}