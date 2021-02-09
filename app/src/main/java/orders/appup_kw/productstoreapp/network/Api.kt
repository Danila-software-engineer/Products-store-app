package orders.appup_kw.productstoreapp.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import orders.appup_kw.productstoreapp.network.model.*
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
    ): Observable<ResponseBody>

/////////////////////////////////////////////////////////////////

    @GET("cart")
    fun getCart(
            @Header("x-apikey") token : String
    ): Observable<List<CartResponse>>

    @DELETE("cart/{id}")
    fun deleteCart(
            @Header("x-apikey") token : String,
            @Path("id") id: String
    ): Observable<ResponseBody>


    @POST("cart")
    fun postCart(
            @Header("x-apikey") token : String,
            @Header("content-type") content_type : String,
            @Body body: CartPosting
    ): Observable<ResponseBody>

    @PATCH("cart/{id}")
    fun patchCart(
        @Header("x-apikey") token : String,
        @Header("content-type") content_type : String,
        @Path("id") id: String,
        @Body body: PatchAmount
    ): Observable<ResponseBody>
}