package dev.rodni.ru.forecastpracticeapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import dev.rodni.ru.forecastpracticeapp.R
import dev.rodni.ru.forecastpracticeapp.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException(applicationContext.getString(R.string.check_internet_connectivity))

        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it !=null && it.isConnected
        }
    }

}