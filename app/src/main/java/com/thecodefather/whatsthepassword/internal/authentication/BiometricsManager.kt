package com.thecodefather.whatsthepassword.internal.authentication

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Helper class for managing Biometric Authentication Process
 */
object BiometricsManager {

    /**
     * Checks if the device has Biometric support
     */
    fun hasBiometricCapability(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK or BIOMETRIC_STRONG)
    }

    /**
     * Checks if Biometric Authentication (example: Fingerprint) is set in the device
     */
    fun isBiometricReady(context: Context) =
        hasBiometricCapability(context) == BiometricManager.BIOMETRIC_SUCCESS

    /**
     * Prepares PromptInfo dialog with provided configuration
     */
    fun setBiometricPromptInfo(
        title: String,
        subtitle: String,
        description: String,
        allowDeviceCredential: Boolean
    ): BiometricPrompt.PromptInfo {
        val builder = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)

        // Use Device Credentials if allowed, otherwise show Cancel Button
        builder.apply {
            if (allowDeviceCredential) setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            else setNegativeButtonText("Cancel")
        }

        return builder.build()
    }

    /**
     * Initializes BiometricPrompt with the caller and callback handlers
     */
    fun initBiometricPrompt(
        activity: AppCompatActivity,
        listener: BiometricAuthListener
    ): BiometricPrompt {
        // Attach calling Activity
        val executor = ContextCompat.getMainExecutor(activity)

        // Attach callback handlers
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                listener.onBiometricAuthenticationError(errorCode, errString.toString())
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.w(this.javaClass.simpleName, "Authentication failed for an unknown reason")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onBiometricAuthenticationSuccess(result)
            }
        }

        return BiometricPrompt(activity, executor, callback)
    }

    /**
     * Displays a BiometricPrompt with provided configurations
     */
    fun showBiometricPrompt(
        title: String = "Biometric Authentication",
        subtitle: String = "Enter biometric credentials to proceed.",
        description: String = "Input your Fingerprint or FaceID to ensure it's you!",
        activity: AppCompatActivity,
        listener: BiometricAuthListener,
        cryptoObject: BiometricPrompt.CryptoObject? = null,
        allowDeviceCredential: Boolean = false
    ) {
        // Prepare BiometricPrompt Dialog
        val promptInfo = setBiometricPromptInfo(
            title,
            subtitle,
            description,
            allowDeviceCredential
        )

        // Attach with caller and callback handler
        val biometricPrompt = initBiometricPrompt(activity, listener)

        // Authenticate with a CryptoObject if provided, otherwise default authentication
        biometricPrompt.apply {
            if (cryptoObject == null) authenticate(promptInfo)
            else authenticate(promptInfo, cryptoObject)
        }
    }

    /**
     * Navigates to Device's Settings screen Biometric Setup
     */
    fun lunchBiometricSettings(context: Context) {
        ActivityCompat.startActivity(
            context,
            Intent(Settings.ACTION_SECURITY_SETTINGS),
            null
        )
    }
}