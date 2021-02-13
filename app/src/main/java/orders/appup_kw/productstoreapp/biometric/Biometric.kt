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
    val observable = PublishSubject.create<String>()

    init {
        initExecutor()
        initBiometricPrompt()
        initPromptInfo()

    }

    private fun initExecutor(){
        executor = ContextCompat.getMainExecutor(context)
    }

    private fun initBiometricPrompt(){
        biometricPrompt = BiometricPrompt(context as FragmentActivity, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int,
                                                       errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        observable.onError(Throwable("Authentication error: $errString"))
                    }

                    override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        observable.onComplete()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        observable.onError(Throwable("Authentication failed"))
                    }

                })
    }

    private fun initPromptInfo(){
        promptInfo = if(android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.Q){
            BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Fingerprint auth")
                    .setSubtitle("Log in using your biometric credential")
                    .setDeviceCredentialAllowed(true)
                    .build()
        }else{
            BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Fingerprint auth")
                    .setSubtitle("Log in using your biometric credential")
                    .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                    .build()
        }
    }

    fun launchFingerprintAuth(): PublishSubject<String> {
        biometricPrompt.authenticate(promptInfo)
        return observable
    }
}