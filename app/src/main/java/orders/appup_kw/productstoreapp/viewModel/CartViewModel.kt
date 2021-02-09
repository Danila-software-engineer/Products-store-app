package orders.appup_kw.productstoreapp.viewModel

import androidx.lifecycle.MutableLiveData
import orders.appup_kw.productstoreapp.applifecycle.App
import orders.appup_kw.productstoreapp.base.BaseViewModel
import orders.appup_kw.productstoreapp.network.model.CartResponse
import orders.appup_kw.productstoreapp.repository.CartRepository
import javax.inject.Inject

class CartViewModel : BaseViewModel() {

    @Inject
    lateinit var cartRepository: CartRepository

    init {
        App.getAppComponent().inject(this)
    }

    val deleteRecItem = MutableLiveData<Int>()

    fun getProducts(){
        compositeDisposable.add(
            cartRepository.getProducts().subscribe(
                {success -> successLiveData.value = success as List<CartResponse>},
                {error -> errorLiveData.value = error}
            )
        )
    }

    fun patchProducts(id: String,amount: Int){
        compositeDisposable.add(
            cartRepository.patchCart(id, amount).subscribe(
                {},
                {error -> errorLiveData.value = error}
            )
        )
    }

    fun deleteProducts(id: String){
        compositeDisposable.add(
            cartRepository.deleteCart(id).subscribe(
                {},
                {error -> errorLiveData.value = error}
            )
        )
    }

    fun deleteRecItem(int: Int){
        deleteRecItem.value = int
    }
}