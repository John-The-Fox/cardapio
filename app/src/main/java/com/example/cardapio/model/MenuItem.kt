package com.example.cardapio.model

import java.io.Serializable

data class MenuItem(
    val name: String,
    val price: Double,
    val imageUrl: String,
    var quantity: Int = 0, // Campo para armazenar a quantidade
    val description: String
): Serializable