package dev.rodni.ru.forecastpracticeapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rodni.ru.forecastpracticeapp.data.db.entity.CURRENT_WEATHER_ID
import dev.rodni.ru.forecastpracticeapp.data.db.entity.CurrentWeatherEntry
import dev.rodni.ru.forecastpracticeapp.data.db.unitlocalized.ImperialCurrentWeatherEntry
import dev.rodni.ru.forecastpracticeapp.data.db.unitlocalized.MetricCurrentWeatherEntry

interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(currentWeatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric() : LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial() : LiveData<ImperialCurrentWeatherEntry>
}