package orders.appup_kw.productstoreapp.network.model

import com.google.gson.annotations.SerializedName

data class CartResponse(
        @SerializedName("_id") val _id : String,
        @SerializedName("amount") val amount : Int,
        @SerializedName("product") val product : Product,
        var isCart: Boolean = false
)

data class Product (
        @SerializedName("id") val id : Int,
        @SerializedName("title") val title : String,
        @SerializedName("price") val price : Double,
        @SerializedName("description") val description : String,
        @SerializedName("category") val category : String,
        @SerializedName("image") val image : String
)

data class CartPosting(
        var amount : Int,
        var product: ProductForCartPosting
)

data class ProductForCartPosting (
        var id : Int?,
        var title : String?,
        var price : Double?,
        var description : String?,
        var category : String?,
        var image : String?
)

data class PatchAmount(
        var amount: Int
)

class MapperForCart {
    companion object {
        fun Products.transformToProductForCartPosting(amount: Int): CartPosting {
            return CartPosting (
                    amount,
                    ProductForCartPosting(
                            id,
                            title,
                            price,
                            description,
                            category,
                            image
                    )
            )
        }
    }
}