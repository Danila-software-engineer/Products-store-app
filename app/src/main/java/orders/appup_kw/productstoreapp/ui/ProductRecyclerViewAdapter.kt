package orders.appup_kw.productstoreapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.R
import orders.appup_kw.productstoreapp.databinding.ProdLayoutBinding
import orders.appup_kw.productstoreapp.databinding.ProductLayoutBinding
import orders.appup_kw.productstoreapp.viewModel.ProductsViewModel

class ProductRecyclerViewAdapter(
    private val context: Context,
    private val products: List<Products?>,
    private val viewModel: ProductsViewModel
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProdLayoutBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.setAllTextView(products[position])
        holder.loadImage(context, products[position])
        setUpButton(holder.binding.favButton, viewModel, products[position])

    }

    private fun setUpButton(view: View, viewModel: ProductsViewModel, product: Products?){
        view.setBackgroundResource(R.drawable.inactivefav_button_png)

        if(product?.isFav == true){
            view.setBackgroundResource(R.drawable.activated)
            view.setOnClickListener {
                product.isFav = false
                it.setBackgroundResource(R.drawable.inactivefav_button_png)
                product.id?.let { prod -> viewModel.deleteFavorites(prod) }
            }
        }else {
            view.setOnClickListener {
                product?.isFav = true
                it.setBackgroundResource(R.drawable.activated)
                product?.let { prod -> viewModel.postFavorites(prod) }
            }
        }
    }

    override fun getItemCount() = products.size

    class ProductViewHolder(var binding: ProdLayoutBinding) : RecyclerView.ViewHolder(binding.root){



        fun setAllTextView(product: Products?){
            binding.name.text = product?.title
            binding.category.text = product?.category
            binding.price.text = product?.price.toString() + "\$"
        }

        fun loadImage(context: Context, product: Products?){
            try {
                Glide.with(context)
                    .load(product?.image)
                    .into(binding.image)
            } catch (e: Exception) {
                e.printStackTrace()
                binding.image.setImageResource(R.drawable.img_not_found)
            }
        }
    }
}

