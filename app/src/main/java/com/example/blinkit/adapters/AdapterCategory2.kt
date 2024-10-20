package com.example.blinkit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blinkit.R
import com.example.blinkit.databinding.OrderAgainItemsBinding
import com.example.blinkit.models.Category2

class AdapterCategory2(
    private val categoryList: List<Category2>,
    private val onAddButtonClick: (Category2) -> Unit // Lambda to handle add button click
) : RecyclerView.Adapter<AdapterCategory2.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: OrderAgainItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = OrderAgainItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]

        holder.binding.apply {
            ivcategoryimage.setImageResource(category.image)
            itemWeight.text = category.gram
            ivcategorytext.text = category.title
            itemprice.text = category.price

            // Set the click listener for the add button
            addButton.setOnClickListener {
                onAddButtonClick(category) // Call the lambda function with the clicked category
            }

            // Optional: You can also set the root view click listener if needed

        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}
