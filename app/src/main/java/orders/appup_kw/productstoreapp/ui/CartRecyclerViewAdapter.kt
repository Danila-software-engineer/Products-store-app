package orders.appup_kw.productstoreapp.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import orders.appup_kw.productstoreapp.R
import orders.appup_kw.productstoreapp.databinding.CartLayoutBinding
import orders.appup_kw.productstoreapp.network.model.CartResponse
import orders.appup_kw.productstoreapp.viewModel.CartViewModel
import java.util.concurrent.TimeUnit

class CartRecyclerViewAdapter(
    private val context: Context,
    private val products: List<CartResponse>,
    private val viewModel: CartViewModel
) : RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {

    var compositeDisposable = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        holder.setAllTextView(products[position])
        holder.loadImage(context, products[position])
        setUpPlusMinusButtons(position, holder)
    }

    private fun setUpPlusMinusButtons(position: Int, holder: CartViewHolder){
        var amount = products[position].amount
        var complete = false

        val observable: Observable<Int> = Observable.create{ emitter ->
            holder.binding.imageButton6.setOnClickListener {
                if(amount>1){
                    emitter.onNext(-1)
                }
                else{
                    emitter.onComplete()
                }
            }
            holder.binding.imageButton7.setOnClickListener { emitter.onNext(1)}
        }

        val disposable: Disposable = observable
            .doOnNext {
                amount += it
                holder.binding.textView2.text = amount.toString()
            }
            .doOnComplete {
                viewModel.deleteProducts(products[position]._id)
                viewModel.deleteRecItem(position)
                complete = true
            }
            .debounce(1, TimeUnit.SECONDS)
            .subscribe {
                if(amount >= 1 && !complete) {
                    viewModel.patchProducts(products[position]._id, amount)
                }
                Log.e("TAGG","PATCH")
            }

        compositeDisposable.add(disposable)
    }

    fun clearDisposable(){
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }



    override fun getItemCount() = products.size

    class CartViewHolder(var binding: CartLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun setAllTextView(product: CartResponse){
            binding.name.text = product.product.title
            binding.category.text = product.product.category
            binding.price.text = product.product.price.toString() + "\$"
            binding.textView2.text = product.amount.toString()
        }


        fun loadImage(context: Context, product: CartResponse){
            try {
                Glide.with(context)
                    .load(product.product.image)
                    .into(binding.image)
            } catch (e: Exception) {
                e.printStackTrace()
                binding.image.setImageResource(R.drawable.img_not_found)
            }
        }
    }




}