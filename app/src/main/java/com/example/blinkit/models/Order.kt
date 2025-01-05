package com.example.blinkit.models


data class Order(
    val cartItems: List<CartItem> = emptyList()
)

data class CartItem(
    val productName: String = "",
    val quantity: Int = 0
)
