package com.thecodefather.whatsthepassword.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.ui.authentication.AuthenticationViewModelFactory
import com.thecodefather.whatsthepassword.ui.base.fragments.BaseNavigationFragment
import org.kodein.di.generic.instance

class HomeFragment : BaseNavigationFragment(R.layout.home_fragment) {

    private val viewModelFactory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        super.initView()
    }

    override fun manageSubscriptions() {
        super.manageSubscriptions()
    }

    override fun manageEvents() {
        super.manageEvents()
    }
}