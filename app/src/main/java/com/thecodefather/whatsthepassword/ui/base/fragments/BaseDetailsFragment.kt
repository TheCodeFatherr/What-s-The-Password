package com.thecodefather.whatsthepassword.ui.base.fragments

import android.os.Bundle
import android.view.View

abstract class BaseDetailsFragment(fragmentLayoutRes: Int): BaseFragment(fragmentLayoutRes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainUiManager.updateActionBarVisibility(true)
        mainUiManager.updateFabVisibility(false)
    }
}