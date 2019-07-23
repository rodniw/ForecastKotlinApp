package dev.rodni.ru.forecastpracticeapp.data.network.response


import com.google.gson.annotations.SerializedName
import dev.rodni.ru.forecastpracticeapp.data.db.entity.FutureWeatherEntry

data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)