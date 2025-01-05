package com.example.blinkit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blinkit.databinding.OrderAgainItemsBinding
import com.example.blinkit.models.Category2
import com.google.firebase.database.FirebaseDatabase

class AdapterCategory2(
    private val categoryList: List<Category2>,
    private val onAddButtonClick: (Category2) -> Unit,
    private val onUpdateCart: (Int) -> Unit,
) : RecyclerView.Adapter<AdapterCategory2.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: OrderAgainItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = OrderAgainItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        var itemCount = category.quantity // Use the existing quantity from the object

        holder.binding.apply {
            // Load the image using Glide
            Glide.with(ivcategoryimage.context)
                .load(category.image)
                .into(ivcategoryimage)

            itemWeight.text = category.gram
            ivcategorytext.text = category.title
            itemprice.text = category.price

            // Initial state
            addButton.visibility = if (itemCount > 0) View.GONE else View.VISIBLE
            llproductcount.visibility = if (itemCount > 0) View.VISIBLE else View.GONE
            tvproductcount.text = itemCount.toString()

            addButton.setOnClickListener {
                itemCount = 1
                addButton.visibility = View.GONE
                llproductcount.visibility = View.VISIBLE
                tvproductcount.text = itemCount.toString()

                // Update quantity in Firebase
                updateCartItemInDatabase(ivcategoryimage.context, category, itemCount)
                category.quantity = itemCount // Update local object
                onAddButtonClick(category)
                onUpdateCart(1)
            }

            tvdecrementcount.setOnClickListener {
                if (itemCount > 0) {
                    itemCount--
                    tvproductcount.text = itemCount.toString()

                    // Update quantity in Firebase
                    updateCartItemInDatabase(ivcategoryimage.context, category, itemCount)
                    category.quantity = itemCount // Update local object
                    onUpdateCart(-1)

                    if (itemCount == 0) {
                        addButton.visibility = View.VISIBLE
                        llproductcount.visibility = View.GONE
                    }
                }
            }

            tvincrementcount.setOnClickListener {
                itemCount++
                tvproductcount.text = itemCount.toString()

                // Update quantity in Firebase
                updateCartItemInDatabase(ivcategoryimage.context, category, itemCount)
                category.quantity = itemCount // Update local object
                onUpdateCart(1)
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    private fun updateCartItemInDatabase(context: Context, category: Category2, quantity: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance()
        val cartRef = database.getReference("Cart").child(userId)

        val updates = mapOf("quantity" to quantity)

        val categoryTitle = category.title ?: "Unknown"
        cartRef.child(categoryTitle).updateChildren(updates)
            .addOnSuccessListener {
                // Successfully updated quantity in Firebase
                Toast.makeText(
                    context,
                    "Cart updated: $categoryTitle (Qty: $quantity)",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                // Failed to update quantity in Firebase
                Toast.makeText(
                    context,
                    "Failed to update cart: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}
