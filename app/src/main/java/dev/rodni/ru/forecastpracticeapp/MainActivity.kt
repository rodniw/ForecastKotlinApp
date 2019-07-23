package dev.rodni.ru.forecastpracticeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val fusedLocationProviderClient: FusedLocationProviderClient by instance()

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    //note in the bottom
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)



    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(null, navController)
    }
}

/*
Иногда переменную нельзя сразу инициализировать, сделать это можно чуть позже.
Для таких случаев придумали новый модификатор lateinit (отложенная инициализация).


private lateinit var button: Button
Переменная обязательно должна быть изменяемой (var).
Не должна относиться к примитивным типам (Int, Double, Float и т.д).
Не должна иметь собственных геттеров/сеттеров.

Подобный подход удобен во многих случаях, избегая проверки на null.
В противном случае пришлось бы постоянно использовать проверку или утверждение !!, что засоряет код.

Если вы обратитесь к переменной до её инициализации,
то получите исключение "lateinit property ... hos not been initialized" вместо NullPointerException.
 */
