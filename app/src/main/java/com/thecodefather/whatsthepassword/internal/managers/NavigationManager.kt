package com.thecodefather.whatsthepassword.internal.managers

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.internal.extensions.crashLog

class NavigationManager(private val navController: NavController) {

    fun updateNavigation(appGraph: NavGraph, @IdRes idRes: Int) {
        appGraph.setStartDestination(idRes)
        navController.popBackStack()
        navController.graph = appGraph
    }

    fun navigateToScreen(directions: NavDirections, @IdRes popUpToResId: Int? = null) {
        try {
            navController.navigate(directions, getNavOptions(popUpToResId))
        } catch (exception: IllegalArgumentException) {
            crashLog("NavigationManager", exception)
        }
    }

    fun navigateToAction(action:Int) {
        try {
            navController.navigate(action)
        } catch (exception: IllegalArgumentException) {
            crashLog("NavigationManager", exception)
        }
    }

    fun navigateUp() {
        try {
            navController.navigateUp()
        } catch (exception: IllegalArgumentException) {
            crashLog("NavigationManager", exception)
        }
    }

    fun popupTo(action:Int) {
        try {
            navController.popBackStack(action, true)
        } catch (exception: IllegalArgumentException) {
            crashLog("NavigationManager", exception)
        }
    }

    fun popBackstack() {
        try {
            navController.popBackStack()
        } catch (exception: IllegalArgumentException) {
            crashLog("NavigationManager", exception)
        }
    }

    private fun getNavOptions(@IdRes popUpToResId: Int?): NavOptions {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)

        if (popUpToResId != null) navOptions.setPopUpTo(popUpToResId, true)

        return navOptions.build()
    }
}
