package orders.appup_kw.productstoreapp.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.Executor

class Biometric(private var context: Context) {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val observable = PublishSubject.create<String>()
    private lateinit var authenticationCallback: BiometricPrompt.AuthenticationCallback

    private val titleOfAuth = "Fingerprint auth"
    private val subtitleOfAuth = "Log in using your biometric credential"

    init {
        initExecutor()
        initAuthenticationCallback()
        initBiometricPrompt()
        initPromptInfoWithCheckVersion()
    }

    private fun initExecutor(){
        executor = ContextCompat.getMainExecutor(context)
    }

    private fun initAuthenticationCallback(){
        authenticationCallback = AuthenticationCallback()
    }

    private fun initBiometricPrompt(){
        biometricPrompt = BiometricPrompt(context as FragmentActivity, executor, authenticationCallback)
    }

    private fun initPromptInfoWithCheckVersion(){
        if(android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.Q){
            initPromptInfoAndroidVersionLower11()
        }else{
            initPromptInfoAndroidVersionIs11()
        }
    }

    private fun initPromptInfoAndroidVersionIs11(){
        promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(titleOfAuth)
                .setSubtitle(subtitleOfAuth)
                .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
    }

    private fun initPromptInfoAndroidVersionLower11(){
        promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(titleOfAuth)
                .setSubtitle(subtitleOfAuth)
                .setDeviceCredentialAllowed(true)
                .build()
    }

    fun launchFingerprintAuth(): PublishSubject<String> {
        biometricPrompt.authenticate(promptInfo)
        return observable
    }



    inner class AuthenticationCallback :BiometricPrompt.AuthenticationCallback(){
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            //An error has occurred
            observable.onError(Throwable("Authentication error: $errString"))
        }

        override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            //Successfully completed
            observable.onComplete()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            //An error has occurred
            observable.onError(Throwable("Authentication failed"))
        }
    }

}