package dev.rodni.ru.forecastpracticeapp.ui.weather.future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.rodni.ru.forecastpracticeapp.data.provider.UnitProvider
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepository

class FutureListWeatherVMFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) :  ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(forecastRepository, unitProvider) as T
    }
}