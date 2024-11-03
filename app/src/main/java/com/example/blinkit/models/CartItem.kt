package com.example.blinkit.models

data class CartItem(
    val productImage: Int,
    val productName: String,
    val productQuantity: String,
    val productPrice: String,
    var productCount: Int
)
