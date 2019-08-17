package dev.rodni.ru.forecastpracticeapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class LifecycleBoundLocationManager(
    lifecycleOwner: LifecycleOwner,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationCallback: LocationCallback
) : LifecycleObserver {

    private val TAG = "LifecycleBoundLocMan"

    init {
        Log.i(TAG, "init")
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val locationRequest = LocationRequest().apply {
        Log.i(TAG, "apply")
        interval = 5000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        Log.i(TAG, "startLocationUpdates")
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun removeLocationUpdates() {
        Log.i(TAG, "removeLocationUpdates")
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}