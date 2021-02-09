package orders.appup_kw.productstoreapp.network.model

import com.google.gson.annotations.SerializedName

data class Products (
    @SerializedName("_id") val _id : String?,
    @SerializedName("id") val id : Int?,
    @SerializedName("title") val title : String?,
    @SerializedName("price") val price : Double?,
    @SerializedName("description") val description : String?,
    @SerializedName("category") val category : String?,
    @SerializedName("image") val image : String?,
    var isFav: Boolean = false
)

data class AnswerFavorite (
    @SerializedName("_id") val _id : String?,
    @SerializedName("id") val id : Int?,
    @SerializedName("title") val title : String?,
    @SerializedName("price") val price : Double?,
    @SerializedName("description") val description : String?,
    @SerializedName("category") val category : String?,
    @SerializedName("image") val image : String?,
    @SerializedName("_created") val _created : String?,
    @SerializedName("_changed") val _changed : String?,
    @SerializedName("_createdby") val _createdby : String?,
    @SerializedName("_changedby") val _changedby : String?,
    @SerializedName("_keywords") val __keywordswords : List<String>?,
    @SerializedName("_tags") val _tags : String?,
    @SerializedName("_version") val _version : Int?
)


data class ProductForPosting (
    var id : Int?,
    var title : String?,
    var price : Double?,
    var description : String?,
    var category : String?,
    var image : String?
)

class Mapper {
    companion object {
        fun Products.transformToProductForPosting(): ProductForPosting {
            return ProductForPosting (
                id,
                title,
                price,
                description,
                category,
                image,
            )
        }
    }
}



