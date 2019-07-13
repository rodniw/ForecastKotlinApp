package dev.rodni.ru.forecastpracticeapp.data.network.response

import com.google.gson.annotations.SerializedName
import dev.rodni.ru.forecastpracticeapp.data.db.entity.CurrentWeatherEntry
import dev.rodni.ru.forecastpracticeapp.data.db.entity.Location


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location
)