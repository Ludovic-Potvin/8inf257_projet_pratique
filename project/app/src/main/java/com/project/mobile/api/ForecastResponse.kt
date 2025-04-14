package com.project.mobile.api

data class ForecastResponse(
    val list: List<ForecastItem>
)

data class ForecastItem(
    val dt_txt: String,  // ex: "2025-04-14 12:00:00"
    val main: Main
)
