package com.example.cardapio.controller

import com.example.cardapio.model.MenuItem

object CartManager {
    val cartItems = mutableListOf<MenuItem>()

    fun addItem(item: MenuItem) {
        // Verifica se o item já está no carrinho
        val cartItem = cartItems.find { it.name == item.name }
        if (cartItem != null) {
            // Incrementa a quantidade
            cartItem.quantity += 1
        } else {
            // Adiciona o item com quantidade inicial 1
            val newItem = item.copy(quantity = 1)
            cartItems.add(newItem)
        }
    }

    fun reduceItem(item: MenuItem){
        if (item.quantity > 1) {
            item.quantity-- // Decrementa a quantidade, mas não remove
        } else {
            cartItems.remove(item)
        }
    }

    fun getItems(): MutableList<MenuItem> {
        return cartItems
    }
}
