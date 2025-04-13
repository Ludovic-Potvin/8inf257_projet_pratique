package com.project.mobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.mobile.data.WeatherForecastResponse
import com.project.mobile.weather.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel : ViewModel() {

    private val _weatherResponse = MutableLiveData<WeatherForecastResponse>()
    val weatherResponse: LiveData<WeatherForecastResponse> get() = _weatherResponse

    fun fetchWeatherForecast(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.weatherApi.getWeatherForecast(lat, lon, apiKey)
                }
                _weatherResponse.postValue(response)
                Log.d("WeatherViewModel", "Weather data fetched successfully: $response")
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weather data: ${e.message}")
            }
        }
    }
}
