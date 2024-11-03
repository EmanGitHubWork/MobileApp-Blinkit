package com.example.blinkit.models

data class Product(
    val title: String,
    val image: Int,
    val gram: String,
    val price: String,
    var count: Int = 0 // Track item count for each product
)
