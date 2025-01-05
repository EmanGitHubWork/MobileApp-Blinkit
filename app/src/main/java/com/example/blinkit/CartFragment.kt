package com.example.blinkit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blinkit.databinding.FragmentCartBinding
import com.example.blinkit.models.Category2
import android.util.Log
import com.example.blinkit.adapters.CartAdapter
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartItems = mutableListOf<Category2>()
    private var grandTotal: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentCartBinding.inflate(inflater, container, false)

            setupRecyclerView()
            fetchCartItemsFromDatabase() // Fetch cart items from Firebase

            // Handle Place Order button click
            binding.btnPlaceOrder.setOnClickListener {
                navigateToPlaceOrderFragment()
            }
        }

        return binding.root
    }

    private fun fetchCartItemsFromDatabase() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "unknown_user"
        val database = FirebaseDatabase.getInstance()
        val cartRef = database.getReference("Cart").child(userId)

        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartItems.clear()
                for (data in snapshot.children) {
                    val item = data.getValue(Category2::class.java)
                    if (item != null) {
                        cartItems.add(item)
                    }
                }
                binding.rvCartItems.adapter?.notifyDataSetChanged() // Notify adapter of changes
                calculateGrandTotal() // Calculate grand total after data changes
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CartFragment", "Error fetching cart items: ${error.message}")
            }
        })
    }

    private fun setupRecyclerView() {
        val cartAdapter = CartAdapter(cartItems) { grandTotal ->
            binding.tvGrandTotal.text = "$${String.format("%.2f", grandTotal)}"
        }
        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    private fun calculateGrandTotal() {
        val total = cartItems.sumOf { item ->
            val cleanedPrice = item.price.trim().replace("[^\\d.]".toRegex(), "")
            val price = cleanedPrice.toDoubleOrNull() ?: 0.0
            price * item.quantity
        }
        grandTotal = total
        binding.tvGrandTotal.text = "$${String.format("%.2f", grandTotal)}"
    }

    private fun navigateToPlaceOrderFragment() {
        val bundle = Bundle().apply {

            putParcelableArrayList("cartItems", ArrayList(cartItems))
        }
        findNavController().navigate(R.id.action_cartFragment_to_orderplaceFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
