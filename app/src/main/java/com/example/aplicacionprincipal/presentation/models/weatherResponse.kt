package com.example.aplicacionprincipal.presentation.models

data class WeatherResponse(
    val location: Location,
    val forecast: Forecast
)

data class Location(
    val name: String,
    val country: String
)

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day
)

data class Day(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)