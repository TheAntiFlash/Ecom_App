package com.example.ecomapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val discountPrice: Int?,
    val quantity: Int,
    val imageUrl: String,
)
