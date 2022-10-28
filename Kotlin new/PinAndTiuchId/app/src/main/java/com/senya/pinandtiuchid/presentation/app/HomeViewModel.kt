package com.senya.pinandtiuchid.presentation.app

import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.senya.pinandtiuchid.MainActivity
import com.senya.pinandtiuchid.MyApp
import java.util.concurrent.Executor

class HomeViewModel : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    init {
        checkIsBiometricExist()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.DeleteLastDigit -> {
                state =
                    state.copy(
                        enteredNumbers = state.enteredNumbers.dropLast(1)
                    )
            }
            is HomeAction.PressDigit -> {
                val newEnteredNumbers = if (state.enteredNumbers.size < 4) {
                    state.enteredNumbers.plus(action.digit)
                } else {
                    state.enteredNumbers
                }
                state = state.copy(enteredNumbers = newEnteredNumbers)

                if (state.enteredNumbers == listOf(1, 2, 3, 4)) {
                    state = state.copy(isAuthorizeByPassword = false, isAuthorized = true)
                }
            }
            HomeAction.OpenBiometricDialog -> {
                biometricPrompt.authenticate(promptInfo)
            }
        }
    }

    private fun setupBiometric() {
        executor = ContextCompat.getMainExecutor(MyApp.applicationContext())

        biometricPrompt = BiometricPrompt(MainActivity.getActivity() as FragmentActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)

                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        state = state.copy(isAuthorizeByPassword = true)
                    } else {
                        Toast.makeText(
                            MyApp.applicationContext(),
                            "Authentication error: ${errString}", Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    state = state.copy(isAuthorized = true)
                    Toast.makeText(
                        MyApp.applicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        MyApp.applicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Ввести пінкод")
            .build()
    }

    private fun checkIsBiometricExist() {
        val biometricManager = BiometricManager.from(MyApp.applicationContext())
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                setupBiometric()
                biometricPrompt.authenticate(promptInfo)
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.d("MY_APP_TAG", "BIOMETRIC_ERROR_NONE_ENROLLED")
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG or android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                }
                enrollIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                MyApp.applicationContext().startActivity(enrollIntent)
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                TODO()
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                TODO()
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                TODO()
            }
        }
    }
}