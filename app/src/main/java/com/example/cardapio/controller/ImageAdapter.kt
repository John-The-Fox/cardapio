package com.example.cardapio.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardapio.R
// Adapter para exibir imagens em um carrossel usando RecyclerView
class ImageAdapter(private val imageUrls: List<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    // ViewHolder: Representa cada item no RecyclerView
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
    // Infla o layout do item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel_image_item, parent, false)
        return ViewHolder(view)
    }
    // Associa os dados (URL da imagem) ao item exibido
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(imageUrls[position])
            .into(holder.imageView)
    }
    // Retorna o número de itens no RecyclerView
    override fun getItemCount(): Int = imageUrls.size
}


