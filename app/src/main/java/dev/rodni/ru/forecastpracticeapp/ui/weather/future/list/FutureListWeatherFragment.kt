package dev.rodni.ru.forecastpracticeapp.ui.weather.future.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.rodni.ru.forecastpracticeapp.R
import dev.rodni.ru.forecastpracticeapp.ui.base.ScopedFragment
import dev.rodni.ru.forecastpracticeapp.util.hide
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: FutureListWeatherViewModel
    private val factory: FutureListWeatherVMFactory by instance()
    //private val factory: CurrentWeatherViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(FutureListWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeatherEntries = viewModel.weatherEntries.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@FutureListWeatherFragment, Observer { location ->
            if(location == null) return@Observer
            updateLocation(location.name)
        })

        futureWeatherEntries.observe(this@FutureListWeatherFragment, Observer { weatherEntries ->
            if(weatherEntries == null) return@Observer

            group_loading_list.hide()
            updateTitleToNextWeek()
        })
    }

    private fun updateLocation(location:String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateTitleToNextWeek() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Следующая неделя"
    }

}
