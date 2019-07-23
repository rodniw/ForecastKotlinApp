package dev.rodni.ru.forecastpracticeapp.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.rodni.ru.forecastpracticeapp.data.network.ConnectivityInterceptor
import dev.rodni.ru.forecastpracticeapp.data.network.response.CurrentWeatherResponse
import dev.rodni.ru.forecastpracticeapp.data.network.response.FutureWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "ce87d753c17b4b31bc164326191007"

//http://api.apixu.com/v1/current.json?key=ce87d753c17b4b31bc164326191007&q=Kazan&lang=ru

interface ApixuWeatherApi {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "ru"
    ) : CurrentWeatherResponse
    //retrofit now can support suspendable functions
            //: Deferred<CurrentWeatherResponse>

    //http://api.apixu.com/v1/forecast.json?key=ce87d753c17b4b31bc164326191007&q=Kazan&days=1
    @GET("forecast.json")
    suspend fun getFutureWeather(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") languageCode: String = "ru"
    ) : FutureWeatherResponse
            //: Deferred<FutureWeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ) : ApixuWeatherApi {
            val requestInterceptor = Interceptor { chain ->  
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.apixu.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApi::class.java)
        }
    }
}