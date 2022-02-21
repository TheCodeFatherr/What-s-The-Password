package com.thecodefather.whatsthepassword.ui.base

import android.os.Bundle
import android.os.UserManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.databinding.ActivityMainBinding
import com.thecodefather.whatsthepassword.internal.ThemeType
import com.thecodefather.whatsthepassword.internal.UserState
import com.thecodefather.whatsthepassword.internal.extensions.getStringResource
import com.thecodefather.whatsthepassword.internal.extensions.hide
import com.thecodefather.whatsthepassword.internal.extensions.show
import com.thecodefather.whatsthepassword.internal.managers.FlowManager
import com.thecodefather.whatsthepassword.internal.managers.MainUiManager
import com.thecodefather.whatsthepassword.internal.managers.NavigationManager
import com.thecodefather.whatsthepassword.internal.managers.ThemesManager
import com.thecodefather.whatsthepassword.internal.viewBinding
import com.thecodefather.whatsthepassword.ui.base.fragments.BaseOnboardingFragment
import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity: AppCompatActivity(), KodeinAware, CoroutineScope,
    BaseOnboardingFragment.RoutingListener {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    protected val binding by viewBinding(ActivityMainBinding::inflate)

    override val kodein: Kodein by kodein()
    private lateinit var navController: NavController
    lateinit var navigationManager: NavigationManager
    protected val mainUiManager by instance<MainUiManager>()

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        when(ThemesManager.getTheme()){
            ThemeType.SystemDefault -> {
                setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
            }
            ThemeType.Light -> {
                setDefaultNightMode(MODE_NIGHT_NO)
            }
            ThemeType.Dark -> {
                setDefaultNightMode(MODE_NIGHT_YES)
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        job = Job()

        initActionBar()

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navigationManager = NavigationManager(navController)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration( //main dashboards
                topLevelDestinationIds = setOf (
                    R.id.navigation_home, R.id.authenticationFragment
                )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        manageUserState(FlowManager.getUserState())
    }

    protected fun manageUserState(userState: UserState) {
        val appGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)
        when (userState) {
            UserState.Initial -> { //start with onboarding
                navigationManager.updateNavigation(appGraph, R.id.onboardingFragment)
            }
            UserState.Onboarded -> { //go to login page
                navigationManager.updateNavigation(appGraph, R.id.authenticationFragment)
            }
            UserState.Authenticated -> { //go to registration if not registered, else to home
                navigationManager.updateNavigation(appGraph, R.id.homeFragment)
                setActionbarTitle(getStringResource(R.string.title_home))
            }
        }
        navController.graph = appGraph

        mainUiManager.updateActionBarVisibility(FlowManager.isUserAuthenticated())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        val profile = this.menu?.findItem(R.id.action_profile)
        val settings = this.menu?.findItem(R.id.action_settings)
        profile?.isVisible = false
        settings?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    protected fun updateMenuItemsVisibility(value: Boolean){
        lifecycleScope.launch {
            delay(200L)
            val profile = menu?.findItem(R.id.action_profile)
            val settings = menu?.findItem(R.id.action_settings)
            profile?.isVisible = value
            settings?.isVisible = value
        }
    }

    override fun onOptionsItemSelected(item: MenuItem)= when (item.itemId) {
        R.id.action_profile -> {
            true
        }
        R.id.action_settings -> {
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        mainUiManager.destroy()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    protected fun setActionbarTitle(title: String) {
        binding.toolbar.title = title
    }

    protected fun hideToolbar() {
        binding.toolbar.hide()
        supportActionBar?.hide()
    }

    protected fun showToolbar() {
        binding.toolbar.show()
        supportActionBar?.show()
    }

    protected fun updateAppLoaderVisibility(isVisible: Boolean) {
        binding.pbLoader.isVisible = isVisible
    }

    override fun goToAuthentication() {
        manageUserState(UserState.Onboarded)
    }

    override fun goToHome() {
        manageUserState(UserState.Authenticated)
    }
}