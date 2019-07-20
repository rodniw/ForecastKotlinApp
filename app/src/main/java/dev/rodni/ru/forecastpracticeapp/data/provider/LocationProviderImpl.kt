package dev.rodni.ru.forecastpracticeapp.data.provider

import dev.rodni.ru.forecastpracticeapp.data.db.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPrefferedLocationString(): String {
        return "Kazan"
    }
}