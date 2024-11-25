package com.example.cardapio.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardapio.R
import com.example.cardapio.databinding.CartItemBinding
import com.example.cardapio.model.MenuItem

class CartItemAdapter(
    private val cartItems: MutableList<MenuItem>,
    private val onQuantityChange: (MenuItem, Boolean) -> Unit,
    private val onRemoveItem: (MenuItem) -> Unit
) : RecyclerView.Adapter<CartItemAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CartItemBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        // Configura as informações do item
        holder.binding.name.text = cartItem.name
        holder.binding.price.text = String.format("R$ %.2f", cartItem.price)
        holder.binding.quantity.text = cartItem.quantity.toString()
        holder.binding.total.text = String.format("R$ %.2f", cartItem.price * cartItem.quantity)

        // Carrega a imagem usando Glide
        Glide.with(holder.itemView.context)
            .load(cartItem.imageUrl) // Substitua pelo campo correto do modelo
            .placeholder(R.drawable.placeholder) // Imagem temporária
            .error(R.drawable.error_image) // Imagem caso o carregamento falhe
            .into(holder.binding.thumbnail)

        // Configura os botões de incremento e decremento
        holder.binding.addButton.setOnClickListener {
            onQuantityChange(cartItem, true)
        }
        holder.binding.removeButton.setOnClickListener {
            onQuantityChange(cartItem, false)
        }
    }

    override fun getItemCount(): Int = cartItems.size
}