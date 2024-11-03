package com.example.blinkit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.blinkit.adapters.ProductAdapter
import com.example.blinkit.databinding.FragmentCategoryProductBinding
import com.example.blinkit.models.Product

class CategoryProductFragment : Fragment() {
    private lateinit var binding: FragmentCategoryProductBinding
    private var productList = listOf<Product>()
    private var cartItemCount = 0 // Track the total items in the cart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryProductBinding.inflate(inflater, container, false)

        // Get category and load products
        val category = arguments?.getString("category")
        productList = when (category) {
            "Vegetable & Fruits" -> constraints.vegetableProducts
            "Dairy & Breakfast" -> constraints.dairyProducts
            else -> emptyList()
        }
        binding.tbSearchFragment.title = category ?: "Category"
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        // Initialize the adapter with required parameters
        binding.rvProducts.adapter = ProductAdapter(
            productList,
            onAddButtonClick = { product -> onAddButtonClicked(product) },
            onUpdateCart = { count -> updateCart(count) }
        )

        // Show/hide "No Products available" message based on the product list
        binding.tvText.visibility = if (productList.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun onAddButtonClicked(product: Product) {
        // Add logic if needed when a product is added
    }

    private fun updateCart(count: Int) {
        // Update the total cart item count


        // Handle cart update logic here
        (activity as MainActivity).updateCart(count)
    }

    fun onCartClicked(){

    }
}
