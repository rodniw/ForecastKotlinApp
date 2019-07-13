package dev.rodni.ru.forecastpracticeapp

import android.app.Application
import dev.rodni.ru.forecastpracticeapp.data.ApixuWeatherApi
import dev.rodni.ru.forecastpracticeapp.data.db.ForecastDatabase
import dev.rodni.ru.forecastpracticeapp.data.network.ConnectivityInterceptor
import dev.rodni.ru.forecastpracticeapp.data.network.ConnectivityInterceptorImpl
import dev.rodni.ru.forecastpracticeapp.data.network.WeatherNetworkDataSource
import dev.rodni.ru.forecastpracticeapp.data.network.WeatherNetworkDataSourceImpl
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepository
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApi(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
    }
}