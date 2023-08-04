package com.example.ecomapp.ui.model

data class ProductItemUiModel(
    val imageResource: Int,
    val title: String,
    val price: Int,
    val quantity: Int,
    val discountPrice: Int,
    val isPriceDiscounted: Boolean
)

