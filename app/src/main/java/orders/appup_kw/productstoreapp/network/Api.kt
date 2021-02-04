package orders.appup_kw.productstoreapp.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import orders.appup_kw.productstoreapp.network.model.AnswerFavorite
import orders.appup_kw.productstoreapp.network.model.ProductForPosting
import orders.appup_kw.productstoreapp.network.model.Products
import retrofit2.http.*

interface Api {

    @GET("products")
    fun getProducts(@Header("x-apikey") token : String): Observable<List<Products>>


    @GET("favorite")
    fun getFavorites(
        @Header("x-apikey") token : String
    ): Observable<List<Products>>

    @DELETE("favorite/{id}")
    fun deleteFavorites(
        @Header("x-apikey") token : String,
        @Path("id") id: String?
    ): Observable<ResponseBody>


    @POST("favorite")
    fun postFavorites(
        @Header("x-apikey") token : String,
        @Header("content-type") content_type : String,
        @Body body: ProductForPosting
    ): Observable<AnswerFavorite>
}