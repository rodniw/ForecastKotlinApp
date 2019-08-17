package dev.rodni.ru.forecastpracticeapp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import dev.rodni.ru.forecastpracticeapp.ui.LifecycleBoundLocationManager
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

private const val MY_PERMISSION_ACCESS_COARSE_LOCATION = 1

class MainActivity : AppCompatActivity(), KodeinAware {

    private val TAG = "MainActivity"

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
        Log.i(TAG, "onCreate")

        //val type = Typeface.createFromAsset(assets, "font/mlight.ttf")

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)
        //bottom_nav.typ
        //toolbar.typeface = type

        NavigationUI.setupActionBarWithNavController(this, navController)

        requestLocationPermission()

        if (hasLocationPermission()) {
            bindLocationManager()
        }
        else
            requestLocationPermission()
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.i(TAG, "onSupportNavigateUp")
        return NavigationUI.navigateUp(navController, null)
    }

    private fun bindLocationManager() {
        Log.i(TAG, "bindLocationManager")
        LifecycleBoundLocationManager(
            this,
            fusedLocationProviderClient, locationCallback
        )
    }

    private fun requestLocationPermission() {
        Log.i(TAG, "requestLocationPermission")
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSION_ACCESS_COARSE_LOCATION
        )
    }

    private fun hasLocationPermission(): Boolean {
        Log.i(TAG, "hasLocationPermission")
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionsResult")
        if (requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                bindLocationManager()
            else
                Toast.makeText(
                    this,
                    getString(R.string.set_loc_permission_manually),
                    Toast.LENGTH_LONG
                ).show()
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
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
