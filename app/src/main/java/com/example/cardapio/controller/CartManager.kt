package com.example.cardapio.controller

import com.example.cardapio.model.MenuItem
// Objeto responsável por gerenciar o carrinho de compras.
object CartManager {
    val cartItems = mutableListOf<MenuItem>()// Lista de itens no carrinho.
    // Adiciona um item ao carrinho, incrementando quantidade se já existe.
    fun addItem(item: MenuItem) {
        // Verifica se o item já está no carrinho
        val cartItem = cartItems.find { it.name == item.name }
        if (cartItem != null) {
            // Incrementa a quantidade
            cartItem.quantity += 1
        } else {
            // Adiciona o item com quantidade inicial 1
            val newItem = item.copy(quantity = 1)// Cria uma nova instância com quantidade inicial.
            cartItems.add(newItem)
        }
    }
    // Reduz a quantidade de um item ou remove do carrinho.
    fun reduceItem(item: MenuItem){
        if (item.quantity > 1) {
            item.quantity-- // Decrementa a quantidade, mas não remove
        } else {
            cartItems.remove(item)
        }
    }
    // Retorna a lista atual de itens no carrinho.
    fun getItems(): MutableList<MenuItem> {
        return cartItems
    }
}
