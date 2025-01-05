package com.example.blinkit

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.blinkit.adapters.AdapterCategory2
import com.example.blinkit.databinding.FragmentOrderAgainBinding
import com.example.blinkit.models.Category2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class OrderAgainFragment : Fragment() {
    private lateinit var binding: FragmentOrderAgainBinding
    private var isBottomNavVisible = true
    private val cartItems = mutableListOf<Category2>()

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
        val database = FirebaseDatabase.getInstance()
        val categoryRef = database.getReference("category2") // Your Firebase table name
        val groceryCategoryList = ArrayList<Category2>()

        categoryRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                groceryCategoryList.clear()
                for (data in snapshot.children) {
                    val category = data.getValue(Category2::class.java)
                    if (category != null) {
                        groceryCategoryList.add(category)
                    }
                }
                val groceryCategoryAdapter = AdapterCategory2(
                    groceryCategoryList,
                    onAddButtonClick = { category -> onAddButtonClicked(category) },
                    onUpdateCart = { count -> updateCart(count) }
                )
                binding.rvCategoryorder.adapter = groceryCategoryAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Log or handle error
                Log.e("Firebase", "Error: ${error.message}")
            }
        })
    }

    private fun onAddButtonClicked(category: Category2) {
        cartItems.add(category) // Add the item to the local cartItems list
        addCartItemToDatabase(category) // Add to Firebase database
        updateCart(0) // Update the cart count with current cart size
    }

    private fun addCartItemToDatabase(category: Category2) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "unknown_user"
        val database = FirebaseDatabase.getInstance()
        val cartRef = database.getReference("Cart").child(userId)
        val title = category.title ?: "unknown_item"
        // Create a cart item map
        val cartItemData = mapOf(
            "title" to category.title,
            "quantity" to category.quantity,
            "price" to category.price
        )

        // Push the item to the Firebase Cart table
        cartRef.child(title).setValue(cartItemData)
            .addOnSuccessListener {
                Log.d("OrderAgainFragment", "Item added to cart database successfully")
            }
            .addOnFailureListener { e ->
                Log.e("OrderAgainFragment", "Failed to add item to cart database: ${e.message}")
            }
    }

    private fun openCart() {
        val cartFragment = CartFragment()
        val args = Bundle()
        args.putParcelableArrayList("cartItems", ArrayList(cartItems))
        cartFragment.arguments = args

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainerView, cartFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun updateCart(count: Int) {
        (activity as? MainActivity)?.updateCart(count)
        Log.d("OrderAgainFragment", "Cart items count before opening cart: ${cartItems.size}")
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
