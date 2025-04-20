package com.project.mobile.weather.domain

import com.project.mobile.weather.data.WeatherRepository
import javax.inject.Inject

class GetWeeklyTemperaturesUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): List<Double?> {
        return repository.getWeeklyTemperatures(city)
    }
}
