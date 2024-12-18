package com.example.cardapio.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardapio.R
import com.example.cardapio.model.MenuItem
// Adapter para exibir o cardápio em um RecyclerView
class MenuAdapter(
    private val items: List<MenuItem>,// Lista de itens do cardápio
    private val onAddClick: (MenuItem) -> Unit,// Callback para adicionar ao carrinho
    private val onItemClick: (MenuItem) -> Unit// Callback para clicar no item
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    // ViewHolder: Representa cada item no RecyclerView
    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.thumbnail)
        val nameView: TextView = view.findViewById(R.id.name)
        val priceView: TextView = view.findViewById(R.id.price)
        val addButton: Button = view.findViewById(R.id.add_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = items[position]
        holder.nameView.text = item.name
        holder.priceView.text = String.format("R$ %.2f", item.price)
        Glide.with(holder.imageView.context).load(item.imageUrl).into(holder.imageView)
        holder.addButton.setOnClickListener {
            onAddClick(item)// Chama a função para adicionar ao carrinho
            val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.bounce)
            holder.addButton.startAnimation(animation)// Animação do botão
        }
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = items.size
}
