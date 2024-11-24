package com.example.cardapio.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardapio.controller.CartItemAdapter
import com.example.cardapio.databinding.ActivityCartBinding
import com.example.cardapio.model.CartItem

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val cartItems = mutableListOf<CartItem>()
    private lateinit var cartAdapter: CartItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura RecyclerView
        cartAdapter = CartItemAdapter(cartItems, ::updateCartItem, ::removeCartItem)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter

        // Exibe o valor total do pedido
        updateTotalPrice()

        // Botão para confirmar o pedido
        binding.confirmOrderButton.setOnClickListener {
            confirmOrder()
        }

        // Simula itens adicionados para teste
        loadCartItems()
    }

    private fun loadCartItems() {
        // Simula itens adicionados no carrinho
        cartItems.add(CartItem("Pizza Margherita", 35.0, 1))
        cartItems.add(CartItem("Coca-Cola", 5.0, 2))
        cartAdapter.notifyDataSetChanged()
        updateTotalPrice()
    }

    private fun updateCartItem(cartItem: CartItem, increment: Boolean) {
        if (increment) {
            cartItem.quantity++
        } else {
            cartItem.quantity--
            if (cartItem.quantity <= 0) {
                cartItems.remove(cartItem)
            }
        }
        cartAdapter.notifyDataSetChanged()
        updateTotalPrice()
    }

    private fun removeCartItem(cartItem: CartItem) {
        cartItems.remove(cartItem)
        cartAdapter.notifyDataSetChanged()
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val total = cartItems.sumOf { it.price * it.quantity }
        binding.totalPriceTextView.text = String.format("Total: R$ %.2f", total)
    }

    private fun confirmOrder() {
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Carrinho vazio!", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Pedido confirmado com sucesso!", Toast.LENGTH_SHORT).show()
        cartItems.clear()
        cartAdapter.notifyDataSetChanged()
        updateTotalPrice()

        // Redireciona de volta para o MenuActivity
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}
