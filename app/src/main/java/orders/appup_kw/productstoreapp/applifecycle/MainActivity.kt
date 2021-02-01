package orders.appup_kw.productstoreapp.applifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import orders.appup_kw.productstoreapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}