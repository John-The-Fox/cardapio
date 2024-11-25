package com.example.cardapio.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardapio.controller.CartItemAdapter
import com.example.cardapio.controller.CartManager
import com.example.cardapio.databinding.ActivityCartBinding
import com.example.cardapio.model.MenuItem

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartItemAdapter
    private lateinit var cartItems: MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa cartItems corretamente
        cartItems = CartManager.getItems()


        // Configura RecyclerView
        cartAdapter = CartItemAdapter(cartItems, ::updateCartItem, ::removeCartItem)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter

        // Atualiza o preço total
        updateTotalPrice()

        // Configura o botão de confirmação
        binding.confirmOrderButton.setOnClickListener {
            confirmOrder()
        }
    }


    private fun loadCartItems() {
        // logica de carregar aqui
        //cartAdapter.notifyDataSetChanged()
        //updateTotalPrice()
    }

    private fun updateCartItem(cartItem: MenuItem, increment: Boolean) {
        if (increment) {
            CartManager.addItem(cartItem)
        } else {
            CartManager.reduceItem(cartItem)
        }
        cartAdapter.notifyDataSetChanged()
        updateTotalPrice()
    }

    private fun removeCartItem(cartItem: MenuItem) {
        CartManager.reduceItem(cartItem)
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
