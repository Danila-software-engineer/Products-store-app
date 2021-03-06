package orders.appup_kw.productstoreapp.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import orders.appup_kw.productstoreapp.base.BaseRepository
import orders.appup_kw.productstoreapp.network.model.*
import orders.appup_kw.productstoreapp.network.model.Mapper.Companion.transformToProductForPosting
import orders.appup_kw.productstoreapp.network.model.MapperForCart.Companion.transformToProductForCartPosting
import orders.appup_kw.productstoreapp.utils.Content_type
import orders.appup_kw.productstoreapp.utils.token

class FavoritesRepository: BaseRepository() {

    fun getFavorites(): Observable<List<Products>> {
        return api.getFavorites(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCart(): Observable<List<CartResponse>> {
        return api.getCart(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun postCart(body: Products): Observable<ResponseBody>{
        val product = body.transformToProductForCartPosting(1)
        return api.postCart(token, Content_type, product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteCart(id: String): Observable<ResponseBody> {
        return api.deleteCart(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}