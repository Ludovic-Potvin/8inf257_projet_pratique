package com.project.mobile.weather.data

data class ForecastResponse(
    val list: List<ForecastItem>
)

data class ForecastItem(
    val dt_txt: String,
    val main: Main
)

data class Main(
    val temp: Double
)
