package com.example.cardapio.model

import java.io.Serializable

data class CartItem(
    val name: String,
    val price: Double,
    var quantity: Int
): Serializable
