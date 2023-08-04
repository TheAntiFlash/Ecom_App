package com.example.ecomapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecomapp.domain.model.Product
import com.example.ecomapp.domain.model.Response.Failure
import com.example.ecomapp.domain.model.Response.Success
import com.example.ecomapp.domain.repository.ProductRepository
import com.example.ecomapp.domain.repository.ProductsResponse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class FirestoreProductRepository @Inject constructor(): ProductRepository {
    override fun getAllProducts(): LiveData<ProductsResponse>? {
        val products = MutableLiveData<ProductsResponse>()
        val productsDocRef = Firebase.firestore.collection("Products")

        productsDocRef.addSnapshotListener { value, e ->
            if(e != null) {
            Log.w("EcomApp", "Listen Failed", e)
                products.value = Failure(e)
            }
            else {
                var productsList = mutableListOf<Product>()

                for(doc in value!!) {
                    productsList.add(Product(
                        id = doc.id,
                        name = doc.data["name"] as String,
                        price = doc.data["price"] as Int,
                        discountPrice = null,
                        imageUrl = doc.data["imageUrl"] as String,
                        quantity = doc.data["quantity"] as Int
                    ))
                }
            }
        }
    }

    override suspend fun addProduct(product: Product): Success<Boolean> {
        return Success(true)
    }
}
