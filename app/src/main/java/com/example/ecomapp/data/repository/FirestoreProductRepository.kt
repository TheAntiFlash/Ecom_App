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
import com.example.ecomapp.domain.repository.ProductResponse
import com.example.ecomapp.domain.repository.ProductsResponse
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception
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
                val productsList = mutableListOf<Product>()

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

    override fun getProduct(id: String): LiveData<ProductResponse> {
        val productsDocRef = Firebase.firestore.collection("Products").document(id)
        val response = MutableLiveData<ProductResponse>(Response.Loading)
        productsDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    response.value = Success(document.toProduct())
                    Log.d("Debug", "GOT PRODUCT ID: ${document.toProduct().name} ")
                } else {
                    response.value = Failure(Exception("No Such Document"))
                    Log.d("Debug", "GOT Failed")

                }
            }
            .addOnFailureListener { exception ->
                Log.d("ProductGet", "get failed with ", exception)
                response.value = Failure(exception)
            }
        return response
    }
}
