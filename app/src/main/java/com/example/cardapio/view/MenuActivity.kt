package com.example.cardapio.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardapio.R
import com.example.cardapio.controller.CartManager
import com.example.cardapio.controller.MenuAdapter
import com.example.cardapio.databinding.ActivityMenuBinding
import com.example.cardapio.model.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var adapter: MenuAdapter
    private val items = mutableListOf<MenuItem>() // Lista para armazenar os itens do menu
    private val cartItems = mutableListOf<MenuItem>() // Lista para carrinho de compras

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura o View Binding
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura a Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true) // Exibe o título definido no XML

        // Configura as Tabs (TabLayout)
        val categories = listOf("Entradas", "Pratos Principais", "Saladas", "Bebidas")
        categories.forEach { category ->
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(category))
        }

        // Configura o RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MenuAdapter(items, ::onAddClick, ::onItemClick) // Conecta o adapter à lista
        binding.recyclerView.adapter = adapter

        // Listener para troca de abas
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.text?.let { category ->
                    loadMenuItemsFromFirestore(category.toString())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Carrega itens da primeira categoria por padrão
        loadMenuItemsFromFirestore(categories.first())
    }

    // Método para inflar o menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    // Método para tratar ações de clique no menu
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                //intent.putExtra("cartItems", ArrayList(cartItems)) // erro
                startActivity(intent)
                true
            }
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadMenuItemsFromFirestore(category: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("menu_items")
            .whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { snapshot ->
                parseFirestoreData(snapshot)
            }
            .addOnFailureListener { exception ->
                Log.e("MenuActivity", "Erro ao carregar itens: ${exception.message}")
            }
    }

    private fun parseFirestoreData(snapshot: QuerySnapshot) {
        items.clear()
        for (document in snapshot) {
            try {
                val price = document.getDouble("price") ?: throw IllegalArgumentException("Invalid price")
                val item = MenuItem(
                    name = document.getString("name") ?: "",
                    price = price,
                    imageUrl = document.getString("imageUrl") ?: "",
                    description = document.getString("description") ?: ""
                )
                items.add(item)
            } catch (e: Exception) {
                Log.e("MenuActivity", "Erro ao processar item: ${e.message}")
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun onAddClick(item: MenuItem) {
        // Lógica ao clicar no botão de adicionar
        CartManager.addItem(item)

        // Animação simples (exemplo de animação de fade no botão)
                Toast.makeText(this, "${item.name} adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
    }

    private fun onItemClick(item: MenuItem) {
        // Lógica ao clicar no item para abrir os detalhes
        val intent = Intent(this, ItemDetailsActivity::class.java)
        intent.putExtra("item", item) // Envie o objeto item para a próxima Activity
        startActivity(intent)
    }
}
