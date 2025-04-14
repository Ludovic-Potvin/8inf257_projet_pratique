package com.project.mobile.api

data class ForecastResponse(
    val list: List<ForecastItem>
)

data class ForecastItem(
    val dt_txt: String,
    val main: Main
)
