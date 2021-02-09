package orders.appup_kw.productstoreapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import orders.appup_kw.productstoreapp.R
import orders.appup_kw.productstoreapp.databinding.FavoriteLayoutBinding
import orders.appup_kw.productstoreapp.databinding.ProdLayoutBinding
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.viewModel.FavoritesViewModel
import orders.appup_kw.productstoreapp.viewModel.ProductsViewModel

class FavoritesRecyclerViewAdapter(
    private val context: Context,
    private val products: List<Products>,
    private val viewModel: FavoritesViewModel
) : RecyclerView.Adapter<FavoritesRecyclerViewAdapter.FavoritesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = FavoriteLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {

        holder.setAllTextView(products[position])
        holder.loadImage(context, products[position])

        setUpButton(holder.binding.favButton, viewModel, products[position])
    }

    private fun setUpButton(view: View, viewModel: FavoritesViewModel, product: Products){
        view.setBackgroundResource(R.drawable.to_bag)

        if(product.isFav) {
            view.setBackgroundResource(R.drawable.inactive_cart_button)
        }
        view.setOnClickListener {
            if(product.isFav) {
                it.setBackgroundResource(R.drawable.to_bag)
                product.id?.let { prod -> viewModel.deleteCart(prod) }
            }else{
                it.setBackgroundResource(R.drawable.inactive_cart_button)
                product.let { prod -> viewModel.postCart(prod) }
            }
            product.isFav = !product.isFav
        }
    }


    override fun getItemCount() = products.size

    class FavoritesViewHolder(var binding: FavoriteLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun setAllTextView(product: Products){
            binding.name.text = product.title
            binding.category.text = product.category
            binding.price.text = product.price.toString() + "\$"
        }


        fun loadImage(context: Context, product: Products){
            try {
                Glide.with(context)
                    .load(product.image)
                    .into(binding.image)
            } catch (e: Exception) {
                e.printStackTrace()
                binding.image.setImageResource(R.drawable.img_not_found)
            }
        }
    }




}