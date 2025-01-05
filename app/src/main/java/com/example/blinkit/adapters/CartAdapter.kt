package com.example.blinkit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blinkit.databinding.ItemCartBinding
import com.example.blinkit.models.Category2

class CartAdapter(
    private val cartItems: List<Category2>,
    private val onGrandTotalUpdated: (Double) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category2) {
            val cleanedPrice = item.price.trim().replace("[^\\d.]".toRegex(), "")
            val price = cleanedPrice.toDoubleOrNull() ?: 0.0
            val quantity = item.quantity

            val totalPrice = price * quantity

            binding.tvItemName.text = item.title
            binding.tvItemPrice.text = "Price: $${String.format("%.2f", totalPrice)}"
            binding.tvItemQuantity.text = "Quantity: $quantity"

            Glide.with(binding.ivItemImage.context)
                .load(item.image)
                .into(binding.ivItemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size
}