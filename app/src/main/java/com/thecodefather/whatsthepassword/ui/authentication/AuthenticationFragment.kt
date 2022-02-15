package com.thecodefather.whatsthepassword.ui.authentication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.databinding.AuthenticationFragmentBinding
import com.thecodefather.whatsthepassword.databinding.FragmentOnboardingBinding
import com.thecodefather.whatsthepassword.internal.analytics.AnalyticsParams
import com.thecodefather.whatsthepassword.internal.viewBinding
import com.thecodefather.whatsthepassword.ui.base.fragments.BaseOnboardingFragment
import com.thecodefather.whatsthepassword.ui.onboarding.OnboardingViewModel
import com.thecodefather.whatsthepassword.ui.onboarding.OnboardingViewModelFactory
import org.kodein.di.generic.instance

class AuthenticationFragment : BaseOnboardingFragment(R.layout.authentication_fragment) {


    private lateinit var viewModel: AuthenticationViewModel
    private val viewModelFactory: AuthenticationViewModelFactory by instance()

    private val binding by viewBinding(AuthenticationFragmentBinding::bind)
    override val screenName = AnalyticsParams.ScreenNames.AUTHENTICATION_SCREEN

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]
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