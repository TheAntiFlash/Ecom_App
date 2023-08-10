package com.example.ecomapp.domain.model

import android.util.Log
import com.example.ecomapp.domain.repository.Products
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot

data class Product(
    val id: String = "",
    val name: String,
    val price: Int,
    val discountPrice: Int?,
    val quantity: Int,
    val imageUrl: String,
) {

    fun toMap() : Map<String, Any> {
        return hashMapOf(
            "name" to name,
            "price" to price,
            "quantity" to quantity,
            "imageUrl" to imageUrl,
        ).also {
            if (discountPrice != null) {
                it["discountPrice"] = discountPrice
            }
        }
    }
}

fun DocumentSnapshot.toProduct(): Product {
    var discountPrice: Int? = null
    try {
        discountPrice = this.getLong("discountPrice")?.toInt()
    } catch (e : Exception) {
        Log.i("Products GET", "DiscountPrice does not exist for ${this.id}", e)
    }


    return Product(
        id = this.id,
        name = this.data?.get("name") as String,
        price = this.getLong("price")?.toInt() ?: 0,
        discountPrice = discountPrice,
        imageUrl = this.data?.get("imageUrl") as String,
        quantity = this.getLong("quantity")?.toInt() ?: 0
    )
}
