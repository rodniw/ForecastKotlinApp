package dev.rodni.ru.forecastpracticeapp.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.rodni.ru.forecastpracticeapp.data.ApixuWeatherApi
import dev.rodni.ru.forecastpracticeapp.data.network.response.CurrentWeatherResponse
import dev.rodni.ru.forecastpracticeapp.data.network.response.FutureWeatherResponse
import dev.rodni.ru.forecastpracticeapp.util.NoInternetException

private const val COUNT_OF_DAYS = 7

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
                //.await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoInternetException) {
            //TODO: show the error with the snackbar
        }
    }

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() =  _downloadedFutureWeather

    override suspend fun fetchFutureWeather(location: String, languageCode: String) {
        try {
            val fetchedFutureWeather = apixuWeatherApi
                .getFutureWeather(location, COUNT_OF_DAYS, languageCode)
            //.await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        } catch (e: NoInternetException) {
            //TODO: show the error with the snackbar
        }
    }
}