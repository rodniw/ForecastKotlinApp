package dev.rodni.ru.forecastpracticeapp.util

import java.io.IOException

class NoInternetException(message: String) : IOException(message)

class LocationPermissionNotGrantedException(message: String) : Exception(message)

class DateNotFoundException : Exception()