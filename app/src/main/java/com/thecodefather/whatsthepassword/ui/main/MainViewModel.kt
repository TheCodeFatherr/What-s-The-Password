package com.thecodefather.whatsthepassword.ui.main

import com.thecodefather.whatsthepassword.data.repositories.local.LocalRepository
import com.thecodefather.whatsthepassword.ui.base.BaseViewModel

class MainViewModel(
    private val localRepository: LocalRepository
) : BaseViewModel() {

    private var currentPage = ""

    fun setCurrentPage(page: String) {
        currentPage = page
    }

    fun getCurrentPage() = currentPage
}