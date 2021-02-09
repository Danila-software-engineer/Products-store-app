package orders.appup_kw.productstoreapp.viewModel

import android.util.Log
import io.reactivex.Observable
import orders.appup_kw.productstoreapp.applifecycle.App
import orders.appup_kw.productstoreapp.base.BaseViewModel
import orders.appup_kw.productstoreapp.network.model.CartResponse
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.repository.FavoritesRepository
import javax.inject.Inject

class FavoritesViewModel : BaseViewModel() {


    @Inject
    lateinit var favoritesRepository: FavoritesRepository

    init {
        App.getAppComponent().inject(this)
    }


    fun getProducts(){
        compositeDisposable.add(
            favoritesRepository.getFavorites().subscribe(
                {success -> getCartForCheckCart( success as List<Products> )},
                {error -> errorLiveData.value = error}
            )
        )
    }

    private fun getCartForCheckCart(list: List<Products>){
        compositeDisposable.add(
            favoritesRepository.getCart()
                .subscribe({ success -> setVisability((success as List<CartResponse>), list) },
                    {error -> errorLiveData.value = error})
        )
    }

    private fun setVisability(results: List<CartResponse>, lists: List<Products>){
        for(result in results){
            for(list in lists){
                if(result.product.id == list.id){
                    list.isFav = true
                }
            }
        }
        successLiveData.value = lists
    }


    fun postCart(body: Products){
        compositeDisposable.add(
            favoritesRepository.postCart(body).subscribe(
                {  },
                { error -> errorLiveData.value = error }
            )
        )
    }


    fun deleteCart(id: Int){
        getCartForDeleting(id)
    }


    private fun getCartForDeleting(id: Int){
        Log.e("TAGG", id.toString())
        compositeDisposable.add(
            favoritesRepository.getCart()
                .flatMap { prod -> Observable.fromIterable(prod)}
                .filter {prod -> prod.product.id == id}
                .doOnNext {
                        prod -> deleteCartEnd(prod._id)
                    prod._id.let { Log.e("TAGG", it) }
                }
                .subscribe({  },
                    {error -> errorLiveData.value = error})
        )
    }

    private fun deleteCartEnd(id: String){
        compositeDisposable.add(
            favoritesRepository.deleteCart(id).subscribe(
                {  },
                { error -> errorLiveData.value = error }
            )
        )
    }
}