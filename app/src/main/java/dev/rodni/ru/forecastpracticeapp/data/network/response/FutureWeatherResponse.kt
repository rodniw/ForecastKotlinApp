package dev.rodni.ru.forecastpracticeapp.data.network.response

import com.google.gson.annotations.SerializedName
import dev.rodni.ru.forecastpracticeapp.data.db.entity.WeatherLocation

data class FutureWeatherResponse(
    //val alert: Alert,
    //val current: Current,
    @SerializedName("forecast")
    val futureWeatherEntries: ForecastDaysContainer,
    val location: WeatherLocation
)