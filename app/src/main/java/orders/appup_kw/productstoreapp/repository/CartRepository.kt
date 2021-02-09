package orders.appup_kw.productstoreapp.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import orders.appup_kw.productstoreapp.base.BaseRepository
import orders.appup_kw.productstoreapp.network.model.CartResponse
import orders.appup_kw.productstoreapp.network.model.PatchAmount
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.utils.Content_type
import orders.appup_kw.productstoreapp.utils.token

class CartRepository: BaseRepository() {

    fun getProducts(): Observable<List<CartResponse>> {
        return api.getCart(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun patchCart(id: String,amount: Int): Observable<ResponseBody>{
        val product = PatchAmount(amount)
        return api.patchCart(token, Content_type, id, product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteCart(id: String): Observable<ResponseBody> {
        return api.deleteCart(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}