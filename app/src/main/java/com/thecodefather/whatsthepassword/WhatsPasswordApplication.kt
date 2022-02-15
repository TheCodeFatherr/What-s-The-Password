package com.thecodefather.whatsthepassword

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.thecodefather.whatsthepassword.data.repositories.local.LocalRepository
import com.thecodefather.whatsthepassword.data.repositories.local.LocalRepositoryImpl
import com.thecodefather.whatsthepassword.data.room_database.AppDatabase
import com.thecodefather.whatsthepassword.internal.managers.AnalyticsManager
import com.thecodefather.whatsthepassword.internal.managers.MainUiManager
import com.thecodefather.whatsthepassword.internal.managers.RoomManager
import com.thecodefather.whatsthepassword.ui.authentication.AuthenticationViewModelFactory
import com.thecodefather.whatsthepassword.ui.home.HomeViewModelFactory
import com.thecodefather.whatsthepassword.ui.onboarding.OnboardingViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class WhatsPasswordApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@WhatsPasswordApplication))

        bind() from singleton { RoomManager() }
        bind() from singleton { MainUiManager() }
        bind() from singleton { AnalyticsManager() }

        bind() from provider { OnboardingViewModelFactory() }
        bind() from provider { AuthenticationViewModelFactory() }
        bind() from provider { HomeViewModelFactory() }

        bind<LocalRepository>() with singleton { LocalRepositoryImpl(instance()) }
    }

    companion object {
        lateinit var context: WhatsPasswordApplication
        lateinit var database: AppDatabase
        lateinit var firebaseAnalytics: FirebaseAnalytics
        private const val ENGLISH = 1
        var language = ENGLISH
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        context = this
        database = AppDatabase(this)
    }
}