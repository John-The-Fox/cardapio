package com.example.cardapio.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapio.model.CartItem

class CartItemAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onQuantityChange: (CartItem, Boolean) -> Unit,
    private val onRemoveItem: (CartItem) -> Unit
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
        holder.binding.itemNameTextView.text = cartItem.name
        holder.binding.itemPriceTextView.text = String.format("R$ %.2f", cartItem.price)
        holder.binding.quantityTextView.text = cartItem.quantity.toString()
        holder.binding.totalPriceTextView.text = String.format("R$ %.2f", cartItem.price * cartItem.quantity)

        // Configura os botões de incremento e decremento
        holder.binding.incrementButton.setOnClickListener {
            onQuantityChange(cartItem, true)
        }
        holder.binding.decrementButton.setOnClickListener {
            onQuantityChange(cartItem, false)
        }

        // Configura o botão de remover item
        holder.binding.removeButton.setOnClickListener {
            onRemoveItem(cartItem)
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
