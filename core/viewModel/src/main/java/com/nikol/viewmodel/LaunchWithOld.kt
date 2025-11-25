package com.nikol.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

private val jobsMap = ConcurrentHashMap<ViewModel, MutableMap<String, WeakReference<Job>>>()
private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
    Log.d("CoroutineError", "Unhandled exception in job '${context.job}': ${throwable.message}")
}

fun ViewModel.launchWithoutOld(
    key: String = "default",
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend CoroutineScope.() -> Unit
): Job {
    cancelJob(key)

    val supervisorJob = SupervisorJob(parent = viewModelScope.coroutineContext[Job])
    val context = dispatcher + supervisorJob + exceptionHandler

    val newJob = viewModelScope.launch(context) {
        block()
    }

    jobsMap.getOrPut(this) {
        ConcurrentHashMap()
    }[key] = WeakReference(newJob)

    return newJob
}

fun ViewModel.cancelJob(key: String = "default") {
    jobsMap[this]?.get(key)?.get()?.cancel()
    jobsMap[this]?.remove(key)
}

fun ViewModel.cancelAllJobs() {
    jobsMap[this]?.values?.forEach { it.get()?.cancel() }
    jobsMap.remove(this)
}

fun ViewModel.isJobActive(key: String = "default"): Boolean {
    return jobsMap[this]?.get(key)?.get()?.isActive ?: false
}
