package com.thecodefather.whatsthepassword.internal.managers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainUiManager {

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> get() = _toolbarTitle
    fun setToolbarTitle(title: String){
        _toolbarTitle.postValue(title)
    }

    private val _actionbarVisibility = MutableLiveData<Boolean>()
    val actionbarVisibility: LiveData<Boolean> get() = _actionbarVisibility
    fun updateActionBarVisibility(isVisible: Boolean){
        _actionbarVisibility.postValue(isVisible)
    }
    private val _fabVisibility = MutableLiveData<Boolean>()
    val fabVisibility: LiveData<Boolean> get() = _fabVisibility
    fun updateFabVisibility(isVisible: Boolean){
        _fabVisibility.postValue(isVisible)
    }

    private val _appLoaderVisibility = MutableLiveData<Boolean>()
    val appLoaderVisibility: LiveData<Boolean> get() = _appLoaderVisibility
    fun updateAppLoaderVisibility(isVisible: Boolean){
        _appLoaderVisibility.postValue(isVisible)
    }

    init {
        _toolbarTitle.value = ""
        _fabVisibility.value = false
    }

    fun destroy() {
        updateAppLoaderVisibility(false)
    }
}
