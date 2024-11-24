package com.example.cardapio.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardapio.controller.MenuAdapter
import com.example.cardapio.databinding.ActivityMenuBinding
import com.example.cardapio.model.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.QuerySnapshot

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var adapter: MenuAdapter
    private val items = mutableListOf<MenuItem>() // Lista para armazenar os itens do menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura o View Binding
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura a Toolbar
        setSupportActionBar(binding.toolbar)

        // Configura as Tabs (TabLayout)
        val categories = listOf("Entradas", "Pratos Principais", "Bebidas", "Saladas")
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
                    imageUrl = document.getString("imageUrl") ?: ""
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
    }

    private fun onItemClick(item: MenuItem) {
        // Lógica ao clicar no item para abrir os detalhes
    }
}
