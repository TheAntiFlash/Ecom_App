package com.example.ecomapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecomapp.domain.model.Product
import com.example.ecomapp.domain.model.Response
import com.example.ecomapp.domain.model.Response.Failure
import com.example.ecomapp.domain.model.Response.Success
import com.example.ecomapp.domain.model.toProduct
import com.example.ecomapp.domain.repository.BooleanResponse
import com.example.ecomapp.domain.repository.ProductRepository
import com.example.ecomapp.domain.repository.ProductsResponse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirestoreProductRepository @Inject constructor(): ProductRepository {
    override fun getAllProducts(): LiveData<ProductsResponse> {
        val products = MutableLiveData<ProductsResponse>()
        val productsDocRef = Firebase.firestore.collection("Products")

        productsDocRef.addSnapshotListener { value, e ->
            if(e != null) {
            Log.w("EcomApp", "Listen Failed", e)
                products.value = Failure(e)
            }
            else {
                var productsList = mutableListOf<Product>()

                for(doc in value!!) { productsList.add(doc.toProduct()) }

                products.value = Success(productsList)
            }
        }
        return products
    }

    override suspend fun addProduct(product: Product): LiveData<BooleanResponse> { //make this livedata
        val productsDocRef = Firebase.firestore.collection("Products")
        val response = MutableLiveData<BooleanResponse>(Success(false))
        productsDocRef
            .add(product.toMap())
            .addOnSuccessListener {
                response.value = Success(true)
            }
            .addOnFailureListener { e ->
                response.value = Failure(e)
                Log.e( "Product", "Error Adding Product", e )
            }.await()

        return response
    }
}
