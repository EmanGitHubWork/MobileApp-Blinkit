package com.example.blinkit

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.blinkit.adapters.AdapterCategory
import com.example.blinkit.databinding.FragmentCategoriesBinding
import com.example.blinkit.models.Category

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        setStatusBarColor()
        setupCategoryRecyclerViews()
        return binding.root
    }

    private fun setupCategoryRecyclerViews() {
        // Setup RecyclerView for Grocery Category
        val groceryCategoryList = ArrayList<Category>()
        for (i in constraints.groceryCategory.indices) {
            groceryCategoryList.add(Category(constraints.groceryCategory[i], constraints.groceryCategoryIcon[i]))
        }
        val groceryCategoryAdapter = AdapterCategory(groceryCategoryList) { category ->
            onCategoryClicked(category)
        }
        binding.rvCategory1.adapter = groceryCategoryAdapter

        // Setup RecyclerView for Snacks Category
        val snacksCategoryList = ArrayList<Category>()
        for (i in constraints.snacksCategory.indices) {
            snacksCategoryList.add(Category(constraints.snacksCategory[i], constraints.snacksCategoryIcon[i]))
        }
        val snacksCategoryAdapter = AdapterCategory(snacksCategoryList) { category ->
            onCategoryClicked(category)
        }
        binding.rvCategory2.adapter = snacksCategoryAdapter


        // Setup RecyclerView for Beauty Category
        val beautyCategoryList = ArrayList<Category>()
        for (i in constraints.beautyCategory.indices) {
            beautyCategoryList.add(Category(constraints.beautyCategory[i], constraints.beautyCategoryIcon[i]))
        }
        val beautyCategoryAdapter = AdapterCategory(beautyCategoryList) { category ->
            onCategoryClicked(category)
        }
        binding.rvCategory3.adapter = beautyCategoryAdapter
    }

    private fun onCategoryClicked(category: Category) {
        // Handle category click here
        // For now, just print the category name or show a Toast
        println("Clicked on category: ${category.title}")
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
