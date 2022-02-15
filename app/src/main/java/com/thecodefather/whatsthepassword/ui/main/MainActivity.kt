package com.thecodefather.whatsthepassword.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.databinding.ActivityMainBinding
import com.thecodefather.whatsthepassword.ui.base.BaseActivity
import org.kodein.di.generic.instance

class MainActivity : BaseActivity() {

    private val viewModelFactory: MainViewModelFactory by instance()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        initViews()
        manageSubscriptions()
        manageEvents()
    }

    private fun initViews() {
    }

    private fun manageSubscriptions() {

        mainUiManager.appLoaderVisibility.observe(this, Observer { isVisible ->
            if (isVisible == null) return@Observer
            updateAppLoaderVisibility(isVisible)
        })

        mainUiManager.toolbarTitle.observe(this, Observer { title ->
            if (title == null) return@Observer
            setActionbarTitle(title)
        })

        mainUiManager.actionbarVisibility.observe(this, Observer {
            if (it == null) return@Observer
            if (it) {
                showToolbar()
            } else {
                hideToolbar()
            }
        })

    }

    private fun manageEvents() {

    }
}