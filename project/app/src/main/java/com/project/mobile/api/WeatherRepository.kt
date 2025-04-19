package com.project.mobile.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {
    private val api = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)

    suspend fun getWeeklyTemperatures(city: String): List<Double?> {
        val response = api.getForecast(city, "9cf7583f90421a4976dd9c5bea58ae3c")

        val dailyTemps = response.list
            .filter { it.dt_txt.contains("21:00:00") }
            .take(7)
            .map { it.main.temp }

        // Réorganisation des températures selon le jour courant
        val todayIndex = (java.time.LocalDate.now().dayOfWeek.value) % 7
        val orderedTemperatures = MutableList<Double?>(7) { null }


        dailyTemps.forEachIndexed { index, temp ->
            val position = (todayIndex + index) % 7
            orderedTemperatures[position] = temp
        }

        println("Températures ordonnées pour $city : $orderedTemperatures")

        return orderedTemperatures
    }



}
