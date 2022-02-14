package com.thecodefather.whatsthepassword.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thecodefather.whatsthepassword.data.repositories.local.LocalRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val localRepository: LocalRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(localRepository) as T
    }
}
