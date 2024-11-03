package com.example.blinkit

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.blinkit.adapters.AdapterCategory
import com.example.blinkit.adapters.AdapterCategory3
import com.example.blinkit.databinding.FragmentNewHomeBinding
import com.example.blinkit.models.Category
import com.example.blinkit.models.Category3
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewHomeFragment : Fragment() {
    private lateinit var binding: FragmentNewHomeBinding
    private var isBottomNavVisible = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewHomeBinding.inflate(layoutInflater, container, false)
        setStatusBarColor()
        setAllBestSellers()
        setAllCategories()
        setProfileImageClickListener()
        setScrollListener() // Add scroll listener
        return binding.root
    }

    private fun setAllBestSellers() {
        val categoryList = ArrayList<Category3>()
        for (i in constraints2.image1.indices) {
            categoryList.add(
                Category3(
                    constraints2.bestSeller[i],
                    constraints2.image1[i],
                    constraints2.image2[i],
                    constraints2.image3[i],
                    constraints2.image4[i]
                )
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

        binding.rvCategory.adapter = AdapterCategory(categoryList, ::OnCategoryIconClick)
    }

    private fun OnCategoryIconClick(category: Category) {
        val bundle = Bundle().apply {
            putString("category", category.title)
        }
        findNavController().navigate(R.id.action_newHome_Fragment_to_categoryProductFragment, bundle)
    }

    private fun setProfileImageClickListener() {
        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_newHome_Fragment_to_profileFragment)
        }
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



    private fun setScrollListener() {
        binding.yourScrollView.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                val bottomNavigationView = activity?.findViewById<View>(R.id.bottom_navigation)
                if (scrollY > oldScrollY && isBottomNavVisible) {
                    bottomNavigationView?.visibility = View.GONE
                    isBottomNavVisible = false
                } else if (scrollY < oldScrollY && !isBottomNavVisible) {
                    bottomNavigationView?.visibility = View.VISIBLE
                    isBottomNavVisible = true
                }
            }
        )
    }
}
