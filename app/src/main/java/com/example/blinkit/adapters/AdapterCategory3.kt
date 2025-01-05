package com.example.blinkit.adapters

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blinkit.databinding.ItemBestsellerBinding
import com.example.blinkit.models.Category3

class AdapterCategory3(
    private val categoryList: List<Category3>,
    private val onCategoryClicked: (Category3) -> Unit
) : RecyclerView.Adapter<AdapterCategory3.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemBestsellerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemBestsellerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            val category = categoryList[position]
            ivproduct1.setImageResource(category.image1)
            ivproduct2.setImageResource(category.image2)
            ivproduct3.setImageResource(category.image3)
            ivproduct4.setImageResource(category.image4)
            ivcategorytext.text = category.title

            root.setOnClickListener {
                onCategoryClicked(category)
            }
        }
    }

    override fun getItemCount(): Int = categoryList.size
}
