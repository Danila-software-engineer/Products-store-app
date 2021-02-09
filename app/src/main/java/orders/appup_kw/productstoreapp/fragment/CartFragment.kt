package orders.appup_kw.productstoreapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import orders.appup_kw.productstoreapp.R
import orders.appup_kw.productstoreapp.base.BaseFragment
import orders.appup_kw.productstoreapp.databinding.FragmentCartBinding
import orders.appup_kw.productstoreapp.databinding.FragmentFavoritesBinding
import orders.appup_kw.productstoreapp.network.model.CartResponse
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.ui.CartRecyclerViewAdapter
import orders.appup_kw.productstoreapp.ui.FavoritesRecyclerViewAdapter
import orders.appup_kw.productstoreapp.viewModel.CartViewModel
import orders.appup_kw.productstoreapp.viewModel.FavoritesViewModel
import java.lang.Exception
import java.util.ArrayList

class CartFragment : BaseFragment<CartViewModel, FragmentCartBinding>() {

    override val viewModel: CartViewModel by viewModels()
    override fun getViewBinding()  = FragmentCartBinding.inflate(layoutInflater)


    lateinit var adapter: CartRecyclerViewAdapter
    var products: MutableList<CartResponse> = ArrayList()

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
        observeDeleteRecItem()

    }

    private fun observeDeleteRecItem(){
        viewModel.deleteRecItem.observe(viewLifecycleOwner){
            products.removeAt(it)
            adapter.notifyDataSetChanged()
        }
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
            (it as List<CartResponse>).let { it1 -> products.addAll(it1)
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
            adapter = CartRecyclerViewAdapter(requireContext(), products, viewModel)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun setUpRecyclerViewAndAdapter(){
        binding.recView.layoutManager = LinearLayoutManager(context)//LLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        binding.recView.adapter = adapter
    }

    override fun onDestroy() {
        adapter.clearDisposable()
        super.onDestroy()

    }



}