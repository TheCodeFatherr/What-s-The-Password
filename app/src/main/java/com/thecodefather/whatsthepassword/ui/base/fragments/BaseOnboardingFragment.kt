package com.thecodefather.whatsthepassword.ui.base.fragments

import android.content.Context
import android.os.Bundle
import android.view.View

open class BaseOnboardingFragment(fragmentLayoutRes: Int): BaseFragment(fragmentLayoutRes) {

    var routingListener: RoutingListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RoutingListener) {
            routingListener = context
        } else {
            throw RuntimeException("$context must implement ${RoutingListener::class.qualifiedName}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainUiManager.updateActionBarVisibility(false)
        mainUiManager.updateBottomBarVisibility(false)
        mainUiManager.updateFabVisibility(false)
    }

    interface RoutingListener {
        fun goToLogin()
        fun goToHome()
        fun goToRegistration()
    }
}