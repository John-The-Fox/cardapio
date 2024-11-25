package com.example.cardapio.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cardapio.R
import com.example.cardapio.controller.CartManager
import com.example.cardapio.model.MenuItem
import com.google.android.material.snackbar.Snackbar

class ItemDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        // Recupera o item passado pela Intent
        val item = intent.getSerializableExtra("item") as? MenuItem

        // Inicializa os componentes da interface
        val itemImage: ImageView = findViewById(R.id.itemImage)
        val itemName: TextView = findViewById(R.id.itemName)
        val itemDescription: TextView = findViewById(R.id.itemDescription)
        val itemPrice: TextView = findViewById(R.id.itemPrice)
        val addToCartButton: Button = findViewById(R.id.addToCartButton)

        // Popula os dados na interface
        item?.let {
            Glide.with(this).load(it.imageUrl).into(itemImage)
            itemName.text = it.name
            itemDescription.text = it.description
            itemPrice.text = String.format("R$ %.2f", it.price)

            // Configura o botão de adicionar ao pedido
            addToCartButton.setOnClickListener {
                CartManager.addItem(item)
                Toast.makeText(this, "${item.name} adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
