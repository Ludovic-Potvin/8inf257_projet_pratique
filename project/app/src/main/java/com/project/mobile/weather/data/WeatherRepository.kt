package com.project.mobile.weather.data

import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApiService
) {
    suspend fun getWeeklyTemperatures(city: String): List<Double?> {
        val response = api.getForecast(
            city = city,
            apiKey = "9cf7583f90421a4976dd9c5bea58ae3c" // Idéalement mettre dans un fichier de config
        )

        val dailyTemps = response.list
            .filter { it.dt_txt.contains("21:00:00") }
            .take(7)
            .map { it.main.temp }

        // Réorganiser selon le jour courant (dimanche = 0)
        val todayIndex = (java.time.LocalDate.now().dayOfWeek.value % 7)
        val orderedTemps = MutableList<Double?>(7) { null }

        dailyTemps.forEachIndexed { index, temp ->
            val pos = (todayIndex + index) % 7
            orderedTemps[pos] = temp
        }
        println("Températures ordonnées par jour réel : $orderedTemps")
        return orderedTemps
    }
}
