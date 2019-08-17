package dev.rodni.ru.forecastpracticeapp.ui.weather.future.detail

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dev.rodni.ru.forecastpracticeapp.R
import dev.rodni.ru.forecastpracticeapp.data.db.LocalDateConverter
import dev.rodni.ru.forecastpracticeapp.ui.base.ScopedFragment
import dev.rodni.ru.forecastpracticeapp.util.DateNotFoundException
import dev.rodni.ru.forecastpracticeapp.util.glide.GlideApp

import kotlinx.android.synthetic.main.future_detail_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

@SuppressLint("SetTextI18n")
class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    private val TAG = "MainActivity"

    override val kodein by kodein()

    private val viewModelFactoryInstanceFactory
            : ((LocalDate) -> FutureDetailWeatherVMFactory) by factory()

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { FutureDetailWeatherFragmentArgs.fromBundle(it) }
        val date = LocalDateConverter.stringToDate(safeArgs?.dateString) ?: throw DateNotFoundException()

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(date))
            .get(FutureDetailWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@FutureDetailWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        futureWeather.observe(this@FutureDetailWeatherFragment, Observer { weatherEntry ->
            if (weatherEntry == null) return@Observer

            updateDate(weatherEntry.date)
            updateTemperatures(weatherEntry.avgTemperature,
                weatherEntry.minTemperature, weatherEntry.maxTemperature)
            updateCondition(weatherEntry.conditionText)
            updatePrecipitation(weatherEntry.totalPrecipitation)
            updateWindSpeed(weatherEntry.maxWindSpeed)
            updateVisibility(weatherEntry.avgVisibilityDistance)
            //updateUv(weatherEntry.uv)

            GlideApp.with(this@FutureDetailWeatherFragment)
                .load("http:" + weatherEntry.conditionIconUrl)
                .into(imageView_condition_icon)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDate(date: LocalDate) {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    private fun updateTemperatures(temperature: Double, min: Double, max: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_min_max_temperature.text = "Мин: $min$unitAbbreviation, Макс: $max$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("мм", "in")
        textView_precipitation.text = "Осадки: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWindSpeed(windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("кмч", "mph")
        textView_wind.text = "Скорость ветра: $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("км", "mi.")
        textView_visibility.text = "Видимость: $visibilityDistance $unitAbbreviation"
    }

    //private fun updateUv(uv: Double) {
    //    textView_uv.text = "UV: $uv"
            //}
}
