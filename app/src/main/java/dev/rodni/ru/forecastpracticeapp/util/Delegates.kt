package dev.rodni.ru.forecastpracticeapp.util

import kotlinx.coroutines.*

fun<T> lazyDeffered(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}