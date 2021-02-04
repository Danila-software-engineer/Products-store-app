package orders.appup_kw.productstoreapp.applifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import orders.appup_kw.productstoreapp.R
import orders.appup_kw.productstoreapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var currentFragmentId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment2) as NavHostFragment? ?: return

        val navController = host.navController


        binding.imageButton.setOnClickListener {
            cleanFooter()
            currentFragmentId = 1
            it.setBackgroundResource(R.drawable.tab_2___shop__1_)
            navController.navigate(R.id.productsFragment)
        }


        binding.imageButton3.setOnClickListener {
            cleanFooter()
            currentFragmentId = 3
            it.setBackgroundResource(R.drawable.tab_4___favorite)
            navController.navigate(R.id.favoritesFragment)
        }
    }

    private fun cleanFooter(){

        when(currentFragmentId){
            1->binding.imageButton.setBackgroundResource(R.drawable.tab_2_shop)
            2->binding.imageButton2.setBackgroundResource(R.drawable.tab_3_bag)
            3->binding.imageButton3.setBackgroundResource(R.drawable.tab_4_favorite)
        }
    }
}