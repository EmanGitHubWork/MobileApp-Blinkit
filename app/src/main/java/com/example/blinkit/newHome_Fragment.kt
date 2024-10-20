package com.example.blinkit

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.blinkit.adapters.AdapterCategory
import com.example.blinkit.adapters.AdapterCategory2
import com.example.blinkit.adapters.AdapterCategory3
import com.example.blinkit.adapters.AdapterProduct
import com.example.blinkit.databinding.FragmentNewHomeBinding
import com.example.blinkit.models.Category
import com.example.blinkit.models.Category3
import com.example.blinkit.models.Product

class NewHomeFragment : Fragment() {
    private lateinit var binding: FragmentNewHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewHomeBinding.inflate(layoutInflater, container, false)
        setStatusBarColor()
        setAllBestSellers()
        setAllCategories()
        setProfileImageClickListener() // Add this line
        return binding.root
    }

    private fun setAllBestSellers() {
        val categoryList = ArrayList<Category3>()
        for (i in constraints2.bestSeller.indices) {
            categoryList.add(Category3(
                constraints2.bestSeller[i],
                constraints2.image1[i],
                constraints2.image2[i],
                constraints2.image3[i],
                constraints2.image4[i])
            )
        }

        val groceryCategoryAdapter = AdapterCategory3(categoryList) { category ->
            // Do nothing on click
        }

        binding.recyclerView.adapter = groceryCategoryAdapter
    }





    private fun setAllCategories() {
        val categoryList = ArrayList<Category>()
        for (i in constraints.allProductsCategoryIcon.indices) {
            categoryList.add(Category(constraints.allProductsCategory[i], constraints.allProductsCategoryIcon[i]))
        }
        val categoryAdapter = AdapterCategory(categoryList) { category ->
            showProductsForCategory(category)
        }
        binding.rvCategory.adapter = categoryAdapter
    }

    private fun setProfileImageClickListener() {
        binding.ivProfile.setOnClickListener {
            // Navigate to ProfileFragment
            val profileFragment = ProfileFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.tbProfileFragment, profileFragment) // Use your container ID
                .addToBackStack(null)
                .commit()
        }
    }



    private fun showProductsForCategory(category: Category) {
        // Hide the category list and bestsellers
        binding.rvCategory.visibility = View.GONE
        binding.bestsellers.visibility = View.GONE
        binding.categorytext.visibility=View.GONE
        // Show the products of the selected category
        val productList = constraints.productsByCategory[category.title] ?: emptyList()
        binding.recyclerView.adapter = AdapterProduct(productList)

        // Set the RecyclerView to be visible
        binding.recyclerView.visibility = View.VISIBLE
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
