package com.example.blinkit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blinkit.databinding.ItemViewProductCategoryBinding
import com.example.blinkit.models.Category

class AdapterCategory(
    private val categoryList: List<Category>,
    private val onCategoryClicked: (Category) -> Unit
) : RecyclerView.Adapter<AdapterCategory.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemViewProductCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemViewProductCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            val category = categoryList[position]
            ivcategoryimage.setImageResource(category.image) // assuming category.image is a drawable resource ID
            ivcategorytext.text = category.title

            root.setOnClickListener {
                onCategoryClicked(category)
            }
        }
    }

    override fun getItemCount(): Int = categoryList.size
}






