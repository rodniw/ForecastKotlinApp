package dev.rodni.ru.forecastpracticeapp.data.provider

import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import dev.rodni.ru.forecastpracticeapp.data.db.entity.WeatherLocation
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val deviceLocationChanged = hasDeviceLocationChanged(lastWeatherLocation)

        return deviceLocationChanged
                //|| hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPrefferedLocationString(): String {
        return "Kazan"
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if(!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getLastDeviceLocation(): Deferred<Location?> {
        return fusedLocationProviderClient.lastLocation
    }
}