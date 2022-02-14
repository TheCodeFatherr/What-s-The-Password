package com.thecodefather.whatsthepassword.ui.base

import androidx.lifecycle.ViewModel
import com.thecodefather.whatsthepassword.internal.extensions.crashLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job

abstract class BaseViewModel: ViewModel() {

    protected var job: Job? = null

    protected val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        crashLog("Exception", throwable)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}