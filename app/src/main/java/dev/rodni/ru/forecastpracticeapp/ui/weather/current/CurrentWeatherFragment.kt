package dev.rodni.ru.forecastpracticeapp.ui.weather.current

//import dev.rodni.ru.forecastpracticeapp.databinding.CurrentWeatherFragmentBinding
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.rodni.ru.forecastpracticeapp.R
import dev.rodni.ru.forecastpracticeapp.ui.base.ScopedFragment
import dev.rodni.ru.forecastpracticeapp.util.glide.GlideApp
import dev.rodni.ru.forecastpracticeapp.util.hide
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

@SuppressLint("SetTextI18n")
class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: CurrentWeatherViewModel
    private val factory: CurrentWeatherViewModelFactory by instance()

    //private lateinit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.current_weather_fragment, container, false)
        //var binding : CurrentWeatherFragmentBinding
        //        = DataBindingUtil.inflate(inflater, R.layout.current_weather_fragment, container, false)
        //viewModel = ViewModelProviders.of(this, factory).get(CurrentWeatherViewModel::class.java)
        //binding.viewmodel = viewModel
        //binding.lifecycleOwner = this
        //return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //setAnimatedVisibility(progressVisible, false)
        viewModel = ViewModelProviders.of(this, factory)
            .get(CurrentWeatherViewModel::class.java)
        //group_loading.hide()
        bindUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer

            group_loading.hide()
            updateLocation("Kazan")
            updateDayToToday()
            updateTemperatures(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(imageView_condition_icon)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDayToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Сегодня"
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Ощущается как $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("мм", "in")
        textView_precipitation.text = "Осадки: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("кмч", "мч")
        textView_wind.text = "Ветер: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("км", "ми")
        textView_visibility.text = "Видимость: $visibilityDistance $unitAbbreviation"
    }
    //@BindingAdapter("android:animatedVisibility")
    //fun setAnimatedVisibility(target: View, isVisible: Boolean) {
    //    target.visibility = if (isVisible) View.VISIBLE else View.GONE
    //}

}
