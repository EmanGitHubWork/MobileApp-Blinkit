package com.example.blinkit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blinkit.databinding.OrderAgainItemsBinding
import com.example.blinkit.models.Category2

class AdapterCategory2(
    private val categoryList: List<Category2>,
    private val onAddButtonClick: (Category2) -> Unit, // Lambda to handle add button click
    private val onUpdateCart: (Int) -> Unit // Lambda to handle cart update
) : RecyclerView.Adapter<AdapterCategory2.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: OrderAgainItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = OrderAgainItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        var itemCount = 0 // Track the count of items for this product

        holder.binding.apply {
            ivcategoryimage.setImageResource(category.image)
            itemWeight.text = category.gram
            ivcategorytext.text = category.title
            itemprice.text = category.price

            // Initial state
            addButton.visibility = View.VISIBLE
            llproductcount.visibility = View.GONE

            // Set the click listener for the add button
            addButton.setOnClickListener {
                itemCount = 1
                addButton.visibility = View.GONE
                llproductcount.visibility = View.VISIBLE
                tvproductcount.text = itemCount.toString()
                onAddButtonClick(category) // Call the lambda function with the clicked category
                onUpdateCart(1) // Update the cart with the new count
            }

            // Set click listeners for increment and decrement buttons
            tvdecrementcount.setOnClickListener {
                if (itemCount > 0) {
                    itemCount--
                    tvproductcount.text = itemCount.toString()
                    onUpdateCart(-1) // Decrease the cart count
                    if (itemCount == 0) {
                        addButton.visibility = View.VISIBLE
                        llproductcount.visibility = View.GONE
                    }
                }
            }

            tvincrementcount.setOnClickListener {
                itemCount++
                tvproductcount.text = itemCount.toString()
                onUpdateCart(1) // Increase the cart count
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}
