package com.thecodefather.whatsthepassword.ui.add_pin

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.databinding.PinFragmentBinding
import com.thecodefather.whatsthepassword.internal.analytics.AnalyticsParams
import com.thecodefather.whatsthepassword.internal.authentication.CryptographyUtil
import com.thecodefather.whatsthepassword.internal.extensions.debug
import com.thecodefather.whatsthepassword.internal.extensions.fastLog
import com.thecodefather.whatsthepassword.internal.extensions.getColorResource
import com.thecodefather.whatsthepassword.internal.extensions.getStringResource
import com.thecodefather.whatsthepassword.internal.viewBinding
import com.thecodefather.whatsthepassword.ui.base.fragments.BaseOnboardingFragment
import org.kodein.di.generic.instance
import java.nio.charset.Charset

class PinFragment : BaseOnboardingFragment(R.layout.pin_fragment) {

    private lateinit var viewModel: PinViewModel
    private val viewModelFactory: PinViewModelFactory by instance()

    private val binding by viewBinding(PinFragmentBinding::bind)
    override val screenName = AnalyticsParams.ScreenNames.ADD_PIN_SCREEN

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, viewModelFactory)[PinViewModel::class.java]
        super.onViewCreated(view, savedInstanceState)
        mainUiManager.updateActionBarVisibility(true)
        mainUiManager.setToolbarTitle("PIN")
    }

    override fun initView() {
        super.initView()
        mainUiManager.updateActionBarVisibility(true)
        mainUiManager.setToolbarTitle("PIN")

        val encrypt = CryptographyUtil.encrypt("123")
        debug("123 Encryption = " + encrypt)
        val decrypt = CryptographyUtil.decrypt(encrypt, "123".toCharArray())
        debug("123 Decryption = " + CryptographyUtil.decrypt(encrypt,"123".toCharArray()))
        decrypt?.let {
            val decryptedString = String(it, Charsets.UTF_8)
            debug("123 Decryption = $decryptedString")
        }
    }

    override fun manageEvents() {
        super.manageEvents()

        binding.swShowPass.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                // show password
                binding.etPassKey.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.etConfirmPassKey.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                // hide password
                binding.etPassKey.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etConfirmPassKey.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }

        binding.etPassKey.addTextChangedListener {
            checkIfPassKeysMatch()
        }

        binding.etConfirmPassKey.addTextChangedListener {
            checkIfPassKeysMatch()
        }
    }

    private fun checkIfPassKeysMatch(){
        val passwordsMatch = !(binding.etConfirmPassKey.text.toString() != binding.etPassKey.text
            .toString() || binding.etPassKey.length() <= 0)

        binding.btSubmitNewKey.isEnabled = passwordsMatch
        if (passwordsMatch) {
            binding.tvKeyMatch.text = getStringResource(R.string.match)
            binding.tvKeyMatch.setTextColor(getColorResource(R.color.colorAccent))
        } else {
            binding.tvKeyMatch.text = getStringResource(R.string.mismatch)
            binding.tvKeyMatch.setTextColor(Color.RED)
        }
    }

}