package dev.rodni.ru.forecastpracticeapp.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import dev.rodni.ru.forecastpracticeapp.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun Group.show() {
    visibility = View.VISIBLE
}

fun Group.hide() {
    visibility = View.GONE
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("ok") {
            snackbar.dismiss()
        }
    }.show()
}