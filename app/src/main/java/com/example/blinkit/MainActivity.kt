package com.example.blinkit

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.blinkit.models.Category2

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var cartLayout: LinearLayout
    private lateinit var tvnumberofproductcount: TextView // TextView for the cart item count
    private lateinit var ivshoppingproductcart: ImageButton // ImageButton for navigating to cart
    private var totalCartItems = 0
    private val cartItems = mutableListOf<Category2>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        cartLayout = findViewById(R.id.llcart)
        tvnumberofproductcount = findViewById(R.id.tvnumberofproductcount)
        ivshoppingproductcart = findViewById(R.id.ivshoppingproductcart)

        // Add destination change listener to control the visibility of cart layout and bottom nav
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Check if we are on the CartFragment
            if (destination.id == R.id.cartFragment) {
                cartLayout.visibility = View.VISIBLE
            } else {
                cartLayout.visibility = View.GONE
            }

            // Handle visibility of bottom navigation based on fragment
            when (destination.id) {
                R.id.newHome_Fragment, R.id.orderAgainFragment, R.id.categoriesFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }

        ivshoppingproductcart.setOnClickListener {
            navigateToCart()
        }
    }



    private var shouldShowCart = false

    fun showCart() {
        shouldShowCart = true
        cartLayout.visibility = View.VISIBLE
    }

    fun updateCart(count: Int) {
        totalCartItems += count // Update the total count based on the passed count
        tvnumberofproductcount.text = totalCartItems.toString()
        cartLayout.visibility = if (totalCartItems > 0) View.VISIBLE else View.GONE
    }

    fun addItemToCart(item: Category2) {
        cartItems.add(item)
        updateCart(1) // Update the cart count
    }

    fun navigateToCart(view: View? = null) { // Accept an optional View parameter
        val bundle = Bundle().apply {
            putParcelableArrayList("cartItems", ArrayList(cartItems))
        }
        navController.navigate(R.id.cartFragment, bundle)
    }
}
