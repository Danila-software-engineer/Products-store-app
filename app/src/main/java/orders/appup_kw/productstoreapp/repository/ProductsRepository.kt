package orders.appup_kw.productstoreapp.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import orders.appup_kw.productstoreapp.base.BaseRepository
import orders.appup_kw.productstoreapp.network.model.AnswerFavorite
import orders.appup_kw.productstoreapp.network.model.Mapper.Companion.transformToProductForPosting
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.utils.Content_type
import orders.appup_kw.productstoreapp.utils.token

class ProductsRepository: BaseRepository() {

    fun getProducts(): Observable<List<Products>> {
        return api.getProducts(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFavorites(): Observable<List<Products>> {
        return api.getFavorites(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun postFavorites(body: Products): Observable<ResponseBody>{
        val product = body.transformToProductForPosting()
        return api.postFavorites(token, Content_type, product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteFavorites(id: String?): Observable<ResponseBody> {
        return api.deleteFavorites(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}