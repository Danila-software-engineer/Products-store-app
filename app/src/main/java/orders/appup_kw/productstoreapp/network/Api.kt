package orders.appup_kw.productstoreapp.network

import io.reactivex.Observable
import orders.appup_kw.productstoreapp.network.model.Products
import retrofit2.http.GET
import retrofit2.http.Header

interface Api {

    @GET("products")
    fun getProducts(@Header("x-apikey") token : String): Observable<List<Products>>
}