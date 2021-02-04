package orders.appup_kw.productstoreapp.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import orders.appup_kw.productstoreapp.base.BaseRepository
import orders.appup_kw.productstoreapp.network.model.AnswerFavorite
import orders.appup_kw.productstoreapp.network.model.Mapper.Companion.transformToProductForPosting
import orders.appup_kw.productstoreapp.network.model.Products
import orders.appup_kw.productstoreapp.utils.Content_type
import orders.appup_kw.productstoreapp.utils.token

class FavoritesRepository: BaseRepository() {

    fun getProducts(): Observable<List<Products>> {
        return api.getFavorites(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}