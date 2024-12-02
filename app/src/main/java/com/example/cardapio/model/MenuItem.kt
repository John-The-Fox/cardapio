package com.example.cardapio.model

import java.io.Serializable
// Modelo de dados para itens do menu.
data class MenuItem(
    val name: String,// Nome do item.
    val price: Double,// Preço do item.
    val imageUrl: String,// URL da imagem.
    var quantity: Int = 0,// Quantidade no carrinho, inicia com 0.
    val description: String// Descrição do item.
): Serializable