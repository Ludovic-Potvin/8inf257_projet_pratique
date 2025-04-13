package com.project.mobile.data

// Modèle représentant la réponse JSON de l'API OpenWeather
data class WeatherForecastResponse(
    val daily: List<DailyWeather>  // Liste des prévisions quotidiennes
)

// Modèle pour les informations météo d'un jour (description et icône)
data class DailyWeather(
    val weather: List<Weather>,  // Liste d'objets météo (par exemple : description, icône)
    val temp: Temp  // Température
)

// Modèle pour les informations sur la température
data class Temp(
    val day: Double  // Température du jour en Kelvin
)

// Modèle pour les informations météo (description et icône)
data class Weather(
    val description: String,  // Description de la météo (ex : "Clear sky")
    val icon: String  // Code de l'icône météo (ex : "01d")
)
