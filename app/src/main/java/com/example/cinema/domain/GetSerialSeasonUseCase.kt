package com.example.cinema.domain

import com.example.cinema.data.SerialRepository
import javax.inject.Inject

class GetSerialSeasonUseCase @Inject constructor(private val repository: SerialRepository) {
    suspend fun getSerials(idSerial: Int) = repository.getSerials(idSerial)
}