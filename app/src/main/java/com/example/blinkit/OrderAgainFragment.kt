package com.example.blinkit

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.example.blinkit.adapters.AdapterCategory2
import com.example.blinkit.databinding.FragmentOrderAgainBinding
import com.example.blinkit.models.Category2

class OrderAgainFragment : Fragment() {
    private lateinit var binding: FragmentOrderAgainBinding
    private var isBottomNavVisible = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderAgainBinding.inflate(layoutInflater, container, false)
        setStatusBarColor()
        setAllCategories()
        setScrollListener()
        return binding.root
    }

    private fun setAllCategories() {
        val groceryCategoryList = ArrayList<Category2>()
        for (i in constraints2.allOrders.indices) {
            groceryCategoryList.add(
                Category2(
                    title = constraints2.allOrders[i],
                    image = constraints2.allOrdersIcon[i],
                    gram = constraints2.allOrdersGram[i],
                    price = constraints2.allOrdersPrice[i]
                )
            )
        }
        val groceryCategoryAdapter = AdapterCategory2(groceryCategoryList,
            onAddButtonClick = { category -> onAddButtonClicked(category) },
            onUpdateCart = { count -> updateCart(count) } // Add this lambda to handle cart updates
        )
        binding.rvCategoryorder.adapter = groceryCategoryAdapter
    }

    private fun onAddButtonClicked(category: Category2) {
        // Handle add button click logic here
    }

    private fun updateCart(count: Int) {
        // Handle cart update logic here
        (activity as MainActivity).updateCart(count)
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
        val bottomNavigationView = activity?.findViewById<View>(R.id.bottom_navigation)

        binding.yourScrollView.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
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
