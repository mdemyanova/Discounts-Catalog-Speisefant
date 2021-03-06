package com.ignation.speisefant.network

import com.ignation.speisefant.database.ProductDatabase
import com.squareup.moshi.Json

data class NetworkProduct(
    val name: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "new_price")
    val newPrice: Int,
    @Json(name = "old_price")
    val oldPrice: Int?,
    val shop: String,
    @Json(name = "start_date")
    val startDate: Long,
    @Json(name = "end_date")
    val endDate: Long,
    val details: String,
    val type: String
)

data class NetworkProductList(val products: List<NetworkProduct>)

fun NetworkProductList.asDatabaseModel(): List<ProductDatabase> {
    return products.map {
        ProductDatabase(
            name = it.name,
            imageUrl = it.imageUrl,
            newPrice = it.newPrice,
            oldPrice = it.oldPrice,
            shop = it.shop,
            startDate = it.startDate,
            endDate = it.endDate,
            details = it.details,
            type = it.type
        )
    }
}
