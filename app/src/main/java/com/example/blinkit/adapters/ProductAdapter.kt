package com.example.blinkit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blinkit.databinding.ItemProductBinding
import com.example.blinkit.models.Product

class AdapterProduct(private var productList: List<Product>) : RecyclerView.Adapter<AdapterProduct.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.ivProductImage.setImageResource(product.imageResId)
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = product.price
            binding.btnAdd.setOnClickListener {
                // Add to cart functionality
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun updateProductList(newProductList: List<Product>) {
        productList = newProductList
        notifyDataSetChanged()
    }
}
