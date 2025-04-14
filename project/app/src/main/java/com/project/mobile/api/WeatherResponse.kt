package com.project.mobile.api

data class WeatherResponse(
    val main: Main
)

data class Main(
    val temp: Double
)
