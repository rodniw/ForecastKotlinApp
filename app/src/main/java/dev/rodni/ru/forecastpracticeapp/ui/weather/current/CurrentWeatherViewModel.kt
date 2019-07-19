package dev.rodni.ru.forecastpracticeapp.ui.weather.current

import androidx.lifecycle.ViewModel;
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepository
import dev.rodni.ru.forecastpracticeapp.util.UnitSystem
import dev.rodni.ru.forecastpracticeapp.util.lazyDeffered

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC//get from settings later

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeffered {
        forecastRepository.getCurrentWeather(isMetric)
    }


}
