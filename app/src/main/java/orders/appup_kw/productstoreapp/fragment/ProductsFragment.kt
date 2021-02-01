package orders.appup_kw.productstoreapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import orders.appup_kw.productstoreapp.base.BaseFragment
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.viewModel.ProductsViewModel
import orders.appup_kw.productstoreapp.databinding.FragmentProductsBinding
import orders.appup_kw.productstoreapp.ui.ProductGridItemDecoration
import orders.appup_kw.productstoreapp.ui.ProductRecyclerViewAdapter
import java.lang.Exception
import java.util.ArrayList


class ProductsFragment : BaseFragment<ProductsViewModel, FragmentProductsBinding>() {


    override val viewModel: ProductsViewModel by viewModels()
    override fun getViewBinding()  = FragmentProductsBinding.inflate(layoutInflater)



    lateinit var adapter: ProductRecyclerViewAdapter
    var products: MutableList<Products?> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getProducts()
        setUpRecViewAndAdapter()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorLiveData.observe(viewLifecycleOwner){
            Toast.makeText(activity,"Error", Toast.LENGTH_SHORT).show();
        }

        viewModel.successLiveData.observe(viewLifecycleOwner){
            try {
                (it as List<Products>).let {it1 -> products.addAll(it1) }
            }catch (e: Exception){
                e.printStackTrace()
            }
            adapter.notifyDataSetChanged()
        }
    }


    private fun setUpRecViewAndAdapter() {
        //initialize adapter
        try {
            adapter = ProductRecyclerViewAdapter(requireContext(), products)
        }catch (e: Exception){
            e.printStackTrace()
        }

        //Set up recyclerView and adapter
        binding.recView.setHasFixedSize(true)
        binding.recView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        binding.recView.adapter = adapter
        val largePadding = 32
        val smallPadding = 32
        binding.recView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
    }

}