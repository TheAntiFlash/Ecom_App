package com.example.ecomapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.ecomapp.domain.model.Product
import com.example.ecomapp.domain.model.Response

typealias Products = List<Product>
typealias ProductsResponse = Response<Products>

interface ProductRepository {
    fun getAllProducts() : LiveData<ProductsResponse>?

    suspend fun addProduct(product: Product) : Response<Boolean>

}