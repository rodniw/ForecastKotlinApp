package dev.rodni.ru.forecastpracticeapp.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.rodni.ru.forecastpracticeapp.R
import dev.rodni.ru.forecastpracticeapp.databinding.CurrentWeatherFragmentBinding
import dev.rodni.ru.forecastpracticeapp.ui.base.ScopedFragment
import dev.rodni.ru.forecastpracticeapp.util.hide
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: CurrentWeatherViewModel
    private val factory: CurrentWeatherViewModelFactory by instance()

    //private lateinit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var binding : CurrentWeatherFragmentBinding
                = DataBindingUtil.inflate(inflater, R.layout.current_weather_fragment, container, false)
        viewModel = ViewModelProviders.of(this, factory)
            .get(CurrentWeatherViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //setAnimatedVisibility(progressVisible, false)
        group_loading.hide()
        bindUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer
        })
    }

    //@BindingAdapter("android:animatedVisibility")
    //fun setAnimatedVisibility(target: View, isVisible: Boolean) {
    //    target.visibility = if (isVisible) View.VISIBLE else View.GONE
    //}

}
