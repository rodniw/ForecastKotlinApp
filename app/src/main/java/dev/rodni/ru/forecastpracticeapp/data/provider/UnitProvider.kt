package dev.rodni.ru.forecastpracticeapp.data.provider

import dev.rodni.ru.forecastpracticeapp.util.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}