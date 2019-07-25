package dev.rodni.ru.forecastpracticeapp.ui.weather.current

import androidx.lifecycle.ViewModel;
import dev.rodni.ru.forecastpracticeapp.data.provider.UnitProvider
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepository
import dev.rodni.ru.forecastpracticeapp.ui.base.WeatherViewModel
import dev.rodni.ru.forecastpracticeapp.util.UnitSystem
import dev.rodni.ru.forecastpracticeapp.util.lazyDeffered

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeffered {
        forecastRepository.getCurrentWeather(isMetricUnit)
    }
}
