package orders.appup_kw.productstoreapp.applifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import orders.appup_kw.productstoreapp.R
import orders.appup_kw.productstoreapp.databinding.ActivityMainBinding
import orders.appup_kw.productstoreapp.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    var currentFragmentId = 1
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        setFragmentId()
        setUpButtons()
    }

    private fun setFragmentId(){
        cleanFooter()
        currentFragmentId = viewModel.fragmentId

        when(currentFragmentId){
            1->setProduct()
            2->setCart()
            3->setFavorite()
        }

    }

    private fun initialize(){
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment2) as NavHostFragment? ?: return

        navController = host.navController
    }

    private fun setUpButtons(){
        binding.imageButton.setOnClickListener {
            setProduct()
        }

        binding.imageButton2.setOnClickListener {
            setCart()
        }

        binding.imageButton3.setOnClickListener {
            setFavorite()
        }
    }

    private fun setProduct(){
        cleanFooter()
        currentFragmentId = 1
        binding.imageButton.setBackgroundResource(R.drawable.tab_2___shop__1_)
        navController.navigate(R.id.productsFragment)
        viewModel.fragmentId = currentFragmentId
    }

    private fun setCart(){
        cleanFooter()
        currentFragmentId = 2
        binding.imageButton2.setBackgroundResource(R.drawable.tab_3___bag__2_)
        navController.navigate(R.id.cartFragment)
        viewModel.fragmentId = currentFragmentId
    }

    private fun setFavorite(){
        cleanFooter()
        currentFragmentId = 3
        binding.imageButton3.setBackgroundResource(R.drawable.tab_4___favorite)
        navController.navigate(R.id.favoritesFragment)
        viewModel.fragmentId = currentFragmentId
    }

    private fun cleanFooter(){
        when(currentFragmentId){
            1->binding.imageButton.setBackgroundResource(R.drawable.tab_2_shop)
            2->binding.imageButton2.setBackgroundResource(R.drawable.tab_3_bag)
            3->binding.imageButton3.setBackgroundResource(R.drawable.tab_4_favorite)
        }
    }
}