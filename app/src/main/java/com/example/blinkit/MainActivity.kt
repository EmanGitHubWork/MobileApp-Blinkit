package com.example.blinkit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var cartLayout: LinearLayout
    private lateinit var tvnumberofproductcount: TextView // TextView for the cart item count
    private lateinit var slider: Slider
    private var totalCartItems = 0

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
        slider = findViewById(R.id.slider)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newHome_Fragment, R.id.orderAgainFragment, R.id.categoriesFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    if (shouldShowCart) {
                        cartLayout.visibility = View.VISIBLE
                    }
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                    cartLayout.visibility = View.GONE
                }
            }
        }

        slider.addOnChangeListener { _, value, _ ->
            updateCart(value.toInt())
        }
    }

    private var shouldShowCart = false

    fun showCart() {
        shouldShowCart = true
        cartLayout.visibility = View.VISIBLE
    }

    fun updateCart(count: Int) {
        totalCartItems = count
        tvnumberofproductcount.text = totalCartItems.toString()
        if (totalCartItems > 0) {
            showCart()
        } else {
            cartLayout.visibility = View.GONE
        }
    }
}
