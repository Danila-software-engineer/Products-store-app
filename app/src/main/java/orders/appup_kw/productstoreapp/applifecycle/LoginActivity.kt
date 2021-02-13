package orders.appup_kw.productstoreapp.applifecycle

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import io.reactivex.disposables.CompositeDisposable
import orders.appup_kw.productstoreapp.R
import orders.appup_kw.productstoreapp.biometric.Biometric
import java.util.concurrent.Executor


class LoginActivity : AppCompatActivity() {

    private lateinit var biometric: Biometric

    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initBiometric()
        observe()
    }

    private fun initBiometric(){
        biometric = Biometric(this)
    }

    private fun observe(){
        compositeDisposable.add(
                biometric.launchFingerprintAuth()
                        .doOnComplete{doOnComplete()}
                        .doOnError{doOnError()}
                        .subscribe()
        )
    }

    private fun doOnError(){
        Toast.makeText(this, "Authentication failed",
                Toast.LENGTH_SHORT)
                .show()
    }

    private fun doOnComplete(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}