package dev.rodni.ru.forecastpracticeapp.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class ScopedFragment : Fragment(), CoroutineScope {

    private val TAG = "ScopedFragment"

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        // + means that our job will run on the main thread
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}