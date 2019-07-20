package dev.rodni.ru.forecastpracticeapp

import android.app.Application
import androidx.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import dev.rodni.ru.forecastpracticeapp.data.ApixuWeatherApi
import dev.rodni.ru.forecastpracticeapp.data.db.ForecastDatabase
import dev.rodni.ru.forecastpracticeapp.data.network.ConnectivityInterceptor
import dev.rodni.ru.forecastpracticeapp.data.network.ConnectivityInterceptorImpl
import dev.rodni.ru.forecastpracticeapp.data.network.WeatherNetworkDataSource
import dev.rodni.ru.forecastpracticeapp.data.network.WeatherNetworkDataSourceImpl
import dev.rodni.ru.forecastpracticeapp.data.provider.LocationProvider
import dev.rodni.ru.forecastpracticeapp.data.provider.LocationProviderImpl
import dev.rodni.ru.forecastpracticeapp.data.provider.UnitProvider
import dev.rodni.ru.forecastpracticeapp.data.provider.UnitProviderImpl
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepository
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepositoryImpl
import dev.rodni.ru.forecastpracticeapp.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        //providers
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl() }
        //db
        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        //network
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApi(instance()) }
        //data source
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        //repos
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance()) }
        //vm factory
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}