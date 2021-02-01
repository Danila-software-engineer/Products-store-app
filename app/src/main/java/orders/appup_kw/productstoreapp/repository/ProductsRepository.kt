package orders.appup_kw.productstoreapp.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import orders.appup_kw.productstoreapp.base.BaseRepository
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.utils.token

class ProductsRepository: BaseRepository() {

    fun getProducts(): Observable<List<Products>> {
        return api.getProducts(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}