package com.thecodefather.whatsthepassword.ui.onboarding

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.data.dtos.WelcomeScreenDto
import com.thecodefather.whatsthepassword.databinding.FragmentOnboardingBinding
import com.thecodefather.whatsthepassword.internal.analytics.AnalyticsParams
import com.thecodefather.whatsthepassword.internal.extensions.debug
import com.thecodefather.whatsthepassword.internal.extensions.showView
import com.thecodefather.whatsthepassword.internal.managers.FlowManager
import com.thecodefather.whatsthepassword.internal.viewBinding
import com.thecodefather.whatsthepassword.ui.base.fragments.BaseOnboardingFragment
import org.kodein.di.generic.instance

class OnboardingFragment : BaseOnboardingFragment(R.layout.fragment_onboarding),
    OnboardingAdapter.Listener {

    private lateinit var viewModel: OnboardingViewModel
    private val viewModelFactory: OnboardingViewModelFactory by instance()

    private lateinit var onboardingAdapter: OnboardingAdapter
    private val screens: List<WelcomeScreenDto> = WelcomeScreenDto. defaultScreens()
    private val binding by viewBinding(FragmentOnboardingBinding::bind)

    override val screenName = AnalyticsParams.ScreenNames.ONBOARDING_SCREEN

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, viewModelFactory).get(OnboardingViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        super.initView()

        onboardingAdapter = OnboardingAdapter(this)
        binding.vpSlider.adapter = onboardingAdapter
        onboardingAdapter.update(screens)
        binding.dotsIndicator.setViewPager(binding.vpSlider)
        binding.vpSlider.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                debug("Page Selected $position")

                binding.tvSkip.isEnabled = screens.size == (position + 1)
            }
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
    })
    }

    override fun manageSubscriptions() {
        super.manageSubscriptions()
    }

    override fun manageEvents() {
        super.manageEvents()

        binding.tvSkip.setOnClickListener {
            FlowManager.setUserOnBoarded(true)
            routingListener?.goToLogin()
        }
    }

    override fun showIndicator() {
        val animation = AnimationUtils
            .loadAnimation(context, R.anim.enter_from_bottom)
        binding.tvSkip.isVisible = true
        binding.tvSkip.startAnimation(animation)
        binding.dotsIndicator.showView()
    }
}