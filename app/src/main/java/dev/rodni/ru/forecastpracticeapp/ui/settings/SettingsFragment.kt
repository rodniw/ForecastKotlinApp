package dev.rodni.ru.forecastpracticeapp.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import dev.rodni.ru.forecastpracticeapp.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity)?.supportActionBar?.title = getString(R.string.settings_text)
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = null
    }
}