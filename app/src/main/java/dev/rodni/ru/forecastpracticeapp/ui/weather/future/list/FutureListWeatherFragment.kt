package dev.rodni.ru.forecastpracticeapp.ui.weather.future.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import dev.rodni.ru.forecastpracticeapp.R
import dev.rodni.ru.forecastpracticeapp.data.db.LocalDateConverter
import dev.rodni.ru.forecastpracticeapp.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import dev.rodni.ru.forecastpracticeapp.ui.base.ScopedFragment
import dev.rodni.ru.forecastpracticeapp.util.hide
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate

class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    private val TAG = "MainActivity"

    override val kodein by kodein()

    private lateinit var viewModel: FutureListWeatherViewModel
    private val factory: FutureListWeatherVMFactory by instance()
    //private val factory: CurrentWeatherViewModelFactory by instance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView")
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "onActivityCreated")
        viewModel = ViewModelProviders.of(this, factory).get(FutureListWeatherViewModel::class.java)

        bindUI()
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach")
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
            initRecyclerView(weatherEntries.toFutureWeatherItems())
        })
    }

    private fun updateLocation(location:String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateTitleToNextWeek() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Следующая неделя"
    }

    private fun List<UnitSpecificSimpleFutureWeatherEntry>.toFutureWeatherItems()
            : List<FutureWeatherItem> {
        //map operator takes every item from the list and convert it
        return this.map {
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? FutureWeatherItem)?.let {
                showWeatherDetail(it.weatherEntry.date, view)
            }
        }
    }

    private fun showWeatherDetail(date: LocalDate, view: View) {
        val dateString = LocalDateConverter.dateToString(date)!!
        val actionDetail = FutureListWeatherFragmentDirections.actionDetail(dateString)
        Navigation.findNavController(view).navigate(actionDetail)
    }

}
