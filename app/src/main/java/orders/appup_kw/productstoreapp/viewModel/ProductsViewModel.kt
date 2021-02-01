package orders.appup_kw.productstoreapp.viewModel


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
                {success -> successLiveData.value = success as List<Products>},
                {error -> errorLiveData.value = error}
            )
        )
    }


}