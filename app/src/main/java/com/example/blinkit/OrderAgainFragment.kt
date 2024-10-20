package com.example.blinkit

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.blinkit.adapters.AdapterCategory2 // Update this import
import com.example.blinkit.databinding.FragmentOrderAgainBinding
import com.example.blinkit.models.Category2

class OrderAgainFragment : Fragment() {
    private lateinit var binding: FragmentOrderAgainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderAgainBinding.inflate(layoutInflater, container, false)
        setStatusBarColor()
        setAllCategories()
        return binding.root
    }

    private fun setAllCategories() {
        // Setup RecyclerView for Grocery Category
        val groceryCategoryList = ArrayList<Category2>()
        for (i in constraints2.allOrders.indices) {
            groceryCategoryList.add(Category2(
                title = constraints2.allOrders[i],
                image = constraints2.allOrdersIcon[i],
                gram = constraints2.allOrdersGram[i],
                price = constraints2.allOrdersPrice[i] // Assuming you have a price array
            ))
        }
        val groceryCategoryAdapter = AdapterCategory2(groceryCategoryList) { category ->
            onAddButtonClicked(category)
        }
        binding.rvCategoryorder.adapter = groceryCategoryAdapter
    }

    private fun onAddButtonClicked(category: Category2) {
        // Handle add button click for the category
        // You can implement any logic here, like adding to cart
    }

    private fun setStatusBarColor() {
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), R.color.yellow)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}
