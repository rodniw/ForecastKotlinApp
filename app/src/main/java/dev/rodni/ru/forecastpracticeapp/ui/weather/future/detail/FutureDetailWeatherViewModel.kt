package dev.rodni.ru.forecastpracticeapp.ui.weather.future.detail

import dev.rodni.ru.forecastpracticeapp.data.provider.UnitProvider
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepository
import dev.rodni.ru.forecastpracticeapp.ui.base.WeatherViewModel
import dev.rodni.ru.forecastpracticeapp.util.lazyDeffered
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    val unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {
    val weather by lazyDeffered {
        forecastRepository.getFutureWeatherList(detailDate, super.isMetricUnit)
    }
}
