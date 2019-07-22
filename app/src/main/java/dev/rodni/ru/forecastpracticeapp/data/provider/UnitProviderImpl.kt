package dev.rodni.ru.forecastpracticeapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dev.rodni.ru.forecastpracticeapp.util.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider  {

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}