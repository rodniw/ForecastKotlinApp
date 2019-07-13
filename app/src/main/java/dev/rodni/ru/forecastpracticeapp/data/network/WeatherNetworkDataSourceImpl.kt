package dev.rodni.ru.forecastpracticeapp.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.rodni.ru.forecastpracticeapp.data.ApixuWeatherApi
import dev.rodni.ru.forecastpracticeapp.data.network.response.CurrentWeatherResponse
import dev.rodni.ru.forecastpracticeapp.util.NoInternetException

class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApi: ApixuWeatherApi
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() =  _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = apixuWeatherApi
                .getCurrentWeather(location, languageCode)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoInternetException) {
            //TODO: show the error with the snackbar
        }
    }
}