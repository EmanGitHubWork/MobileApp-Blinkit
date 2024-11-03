package com.example.blinkit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blinkit.databinding.OrderAgainItemsBinding
import com.example.blinkit.models.Product

class ProductAdapter(
    private val categoryList: List<Product>,
    private val onAddButtonClick: (Product) -> Unit, // Lambda to handle add button click
    private val onUpdateCart: (Int) -> Unit // Lambda to handle cart update
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: OrderAgainItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = OrderAgainItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = categoryList[position]

        holder.binding.apply {
            ivcategoryimage.setImageResource(product.image)
            itemWeight.text = product.gram
            ivcategorytext.text = product.title
            itemprice.text = product.price

            // Update the initial state based on product count
            if (product.count > 0) {
                addButton.visibility = View.GONE
                llproductcount.visibility = View.VISIBLE
                tvproductcount.text = product.count.toString()
            } else {
                addButton.visibility = View.VISIBLE
                llproductcount.visibility = View.GONE
            }

            // Set the click listener for the add button
            addButton.setOnClickListener {
                product.count = 1
                addButton.visibility = View.GONE
                llproductcount.visibility = View.VISIBLE
                tvproductcount.text = product.count.toString()
                onAddButtonClick(product) // Call the lambda function with the clicked product
                onUpdateCart(1) // Update the cart with the new count
            }

            // Set click listeners for increment and decrement buttons
            tvdecrementcount.setOnClickListener {
                if (product.count > 0) {
                    product.count--
                    tvproductcount.text = product.count.toString()
                    onUpdateCart(-1) // Decrease the cart count
                    if (product.count == 0) {
                        addButton.visibility = View.VISIBLE
                        llproductcount.visibility = View.GONE
                    }
                }
            }

            tvincrementcount.setOnClickListener {
                product.count++
                tvproductcount.text = product.count.toString()
                onUpdateCart(1) // Increase the cart count
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}
