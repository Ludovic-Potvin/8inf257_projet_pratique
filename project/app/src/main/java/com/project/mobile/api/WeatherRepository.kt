package com.project.mobile.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {
    private val api = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)

    suspend fun getTemperature(city: String): Double {
        return api.getWeather(city, "9cf7583f90421a4976dd9c5bea58ae3c").main.temp
    }

    suspend fun getWeeklyTemperatures(city: String): List<Double> {
        val response = api.getForecast(city, "9cf7583f90421a4976dd9c5bea58ae3c")

        val dailyTemps = response.list
            .filter { it.dt_txt.contains("21:00:00") }
            .take(7)
            .map { it.main.temp }

        println("Températures récupérées pour $city : $dailyTemps")

        return dailyTemps
    }


}
