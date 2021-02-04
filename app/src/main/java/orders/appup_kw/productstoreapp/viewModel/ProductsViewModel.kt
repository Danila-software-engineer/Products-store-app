package orders.appup_kw.productstoreapp.viewModel


import android.util.Log
import io.reactivex.Observable
import orders.appup_kw.productstoreapp.applifecycle.App
import orders.appup_kw.productstoreapp.base.BaseViewModel
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.repository.ProductsRepository
import javax.inject.Inject

class ProductsViewModel: BaseViewModel() {
    @Inject
    lateinit var productsRepository: ProductsRepository

    init {
        App.getAppComponent().inject(this)
    }


    fun getProducts(){
        compositeDisposable.add(
            productsRepository.getProducts().subscribe(
                { success -> getFavoritesForCheckFavorites(success as List<Products>)},
                { error -> errorLiveData.value = error }
            )
        )
    }

    private fun getFavoritesForCheckFavorites(list: List<Products>){
        compositeDisposable.add(
            productsRepository.getFavorites()
                .flatMap { prod -> Observable.fromIterable(prod) }
                .doOnComplete { successLiveData.value = list }
                .subscribe({ success -> (success as Products).id?.let { list[it].isFav = true } },
                    {error -> errorLiveData.value = error})
        )
    }

    fun postFavorites(body: Products){
        compositeDisposable.add(
            productsRepository.postFavorites(body).subscribe(
                {  },
                { error -> errorLiveData.value = error }
            )
        )
    }


    fun deleteFavorites(id: Int){
        getFavoritesForDeleting(id-1)
    }


    private fun getFavoritesForDeleting(id: Int){
        Log.e("TAGG", id.toString())
        compositeDisposable.add(
            productsRepository.getFavorites()
                .flatMap { prod -> Observable.fromIterable(prod)}
                .filter {prod -> prod.id == id}
                .doOnNext { prod -> deleteFavorite(prod._id)
                    prod._id?.let { Log.e("TAGG", it) }
                }
                .subscribe({  },
                    {error -> errorLiveData.value = error})
        )
    }

    private fun deleteFavorite(id: String?){
        compositeDisposable.add(
            productsRepository.deleteFavorites(id).subscribe(
                {  },
                { error -> errorLiveData.value = error }
            )
        )
    }
}