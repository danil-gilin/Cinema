package com.example.cinema.domain

import com.example.cinema.data.FilterRepository
import com.example.cinema.entity.filterEntity.Filter
import javax.inject.Inject

class FilterLocalUseCase @Inject constructor(private val filterRepository: FilterRepository) {
    fun getFilterLocal() = filterRepository.getFilterLocal()
    fun setFilterLocal(filter: Filter) = filterRepository.setFilterLocal(filter)
}