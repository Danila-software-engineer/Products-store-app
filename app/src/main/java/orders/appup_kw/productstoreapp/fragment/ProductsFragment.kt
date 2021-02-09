package orders.appup_kw.productstoreapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import orders.appup_kw.productstoreapp.base.BaseFragment
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.viewModel.ProductsViewModel
import orders.appup_kw.productstoreapp.databinding.FragmentProductsBinding
import orders.appup_kw.productstoreapp.ui.ProductRecyclerViewAdapter
import java.lang.Exception
import java.util.ArrayList


class ProductsFragment : BaseFragment<ProductsViewModel, FragmentProductsBinding>() {


    override val viewModel: ProductsViewModel by viewModels()
    override fun getViewBinding()  = FragmentProductsBinding.inflate(layoutInflater)



    lateinit var adapter: ProductRecyclerViewAdapter
    var products: MutableList<Products> = ArrayList()


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
        observeError()
        observeSuccess()
    }

    private fun observeError(){
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            it?.let { it1 -> error(it1) }
        }
    }

    private fun observeSuccess(){
        viewModel.successLiveData.observe(viewLifecycleOwner){
            it?.let { it1 -> success(it1) }
        }
    }

    private fun error(it: Throwable){
        Toast.makeText(activity,"Error: $it", Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility = View.GONE
    }

    private fun success(it: Any){
        try {
            (it as List<Products>).let {it1 -> products.addAll(it1)
                binding.progressBar.visibility = View.GONE}
        }catch (e: Exception){
            e.printStackTrace()
        }
        adapter.notifyDataSetChanged()
    }

    private fun setUpRecViewAndAdapter() {
        initializeAdapter()
        setUpRecyclerViewAndAdapter()
    }

    private fun initializeAdapter(){
        try {
            adapter = ProductRecyclerViewAdapter(requireContext(), products, viewModel)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun setUpRecyclerViewAndAdapter(){
        binding.recView.layoutManager = LinearLayoutManager(context)//LLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        binding.recView.adapter = adapter
    }



}