package com.thecodefather.whatsthepassword.internal.authentication

import androidx.biometric.BiometricPrompt

interface BiometricAuthListener {
    fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult)
    fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String)
}