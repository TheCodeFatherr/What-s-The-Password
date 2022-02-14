package com.thecodefather.whatsthepassword.ui.base.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.fragment.findNavController
import com.thecodefather.whatsthepassword.WhatsPasswordApplication
import com.thecodefather.whatsthepassword.internal.managers.AnalyticsManager
import com.thecodefather.whatsthepassword.internal.managers.MainUiManager
import com.thecodefather.whatsthepassword.internal.managers.NavigationManager
import com.thecodefather.whatsthepassword.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment(fragmentLayoutRes: Int) : Fragment(fragmentLayoutRes), KodeinAware,
    CoroutineScope,
    LifecycleObserver {

    val mainUiManager by instance<MainUiManager>()
    lateinit var navigationManager: NavigationManager
    private val analyticsManager by instance<AnalyticsManager>()
    protected val sharedViewModel: MainViewModel by activityViewModels()

    override val kodein: Kodein by kodein(WhatsPasswordApplication.context)
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    open val screenName: String? = null
    open val params: MutableMap<String, String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        analyticsManager.logScreenVisited(screenName, params)
        screenName?.let {
            sharedViewModel.setCurrentPage(it)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationManager = NavigationManager(findNavController())
        initView()
        manageSubscriptions()
        manageEvents()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        activity?.lifecycle?.removeObserver(this)
    }

    open fun initView() {}

    open fun manageSubscriptions() {}

    open fun manageEvents() {}

    fun showAppLoader() {
        mainUiManager.updateAppLoaderVisibility(true)
    }

    fun hideAppLoader() {
        mainUiManager.updateAppLoaderVisibility(false)
    }
}