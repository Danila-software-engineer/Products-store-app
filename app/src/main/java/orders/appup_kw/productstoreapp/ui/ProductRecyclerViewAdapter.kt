package orders.appup_kw.productstoreapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.R
import orders.appup_kw.productstoreapp.databinding.ProductLayoutBinding

class ProductRecyclerViewAdapter(
    private val context: Context,
    private val products: List<Products?>
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductLayoutBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {


        holder.binding.nameOfProduct.text = products[position]?.title
        holder.binding.category.text = products[position]?.category
        holder.binding.description.text = products[position]?.description
        holder.binding.price.text = products[position]?.price.toString()


        try {
            Glide.with(context)
                .load(products[position]?.image)
                .into(holder.binding.productImage)
        } catch (e: Exception) {
            e.printStackTrace()
            holder.binding.productImage.setImageResource(R.drawable.img_not_found)
        }
    }

    override fun getItemCount() = products.size

    class ProductViewHolder(var binding: ProductLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}

