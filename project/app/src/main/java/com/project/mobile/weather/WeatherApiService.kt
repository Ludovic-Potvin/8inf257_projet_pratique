package com.project.mobile.weather

import com.project.mobile.data.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("onecall")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,  // Latitude de la ville
        @Query("lon") lon: Double,  // Longitude de la ville
        @Query("appid") apiKey: String,  // Clé API
        @Query("units") units: String = "metric"  // Unités (Celsius, etc.)
    ): WeatherForecastResponse
}
