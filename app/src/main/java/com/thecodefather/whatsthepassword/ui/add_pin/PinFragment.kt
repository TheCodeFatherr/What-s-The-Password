package com.thecodefather.whatsthepassword.ui.add_pin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.databinding.AuthenticationFragmentBinding
import com.thecodefather.whatsthepassword.databinding.PinFragmentBinding
import com.thecodefather.whatsthepassword.internal.analytics.AnalyticsParams
import com.thecodefather.whatsthepassword.internal.viewBinding
import com.thecodefather.whatsthepassword.ui.authentication.AuthenticationViewModelFactory
import com.thecodefather.whatsthepassword.ui.base.fragments.BaseOnboardingFragment
import org.kodein.di.generic.instance

class PinFragment : BaseOnboardingFragment(R.layout.pin_fragment) {

    private lateinit var viewModel: PinViewModel
    private val viewModelFactory: PinViewModelFactory by instance()

    private val binding by viewBinding(PinFragmentBinding::bind)
    override val screenName = AnalyticsParams.ScreenNames.ADD_PIN_SCREEN

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, viewModelFactory)[PinViewModel::class.java]
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        super.initView()
        mainUiManager.updateActionBarVisibility(true)
        mainUiManager.setToolbarTitle("PIN")
    }

    override fun manageSubscriptions() {
        super.manageSubscriptions()
    }

    override fun manageEvents() {
        super.manageEvents()
    }

}