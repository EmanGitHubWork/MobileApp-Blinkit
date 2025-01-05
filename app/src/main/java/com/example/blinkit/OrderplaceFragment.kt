package com.example.blinkit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.blinkit.databinding.FragmentOrderplaceBinding
import com.example.blinkit.models.Category2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class OrderplaceFragment : Fragment() {
    private lateinit var binding: FragmentOrderplaceBinding
    private val db = FirebaseDatabase.getInstance().reference
    private lateinit var auth: FirebaseAuth

    private val cartItems = mutableListOf<Category2>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderplaceBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        // Retrieve cart items from Firebase Realtime Database
        fetchCartItems()

        // Handle Confirm Order button click
        binding.btnConfirmOrder.setOnClickListener {
            val address = binding.etAddress.text.toString().trim()
            val phoneNumber = binding.etPhoneNumber.text.toString().trim()

            if (address.isNotEmpty() && phoneNumber.isNotEmpty()) {
                saveOrderToRealtimeDB(address, phoneNumber)
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun fetchCartItems() {
        val userId = auth.currentUser?.uid ?: return

        // Fetch cart items for the logged-in user
        db.child("Cart").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartItems.clear()

                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Category2::class.java)
                    if (item != null) {
                        cartItems.add(item)
                    }
                }

                // Log cart items for debugging
                Log.d("OrderplaceFragment", "Cart items fetched: ${cartItems.size}")
                cartItems.forEach { item ->
                    Log.d("OrderplaceFragment", "Item: ${item.title}, Quantity: ${item.quantity}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch cart items: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveOrderToRealtimeDB(address: String, phoneNumber: String) {
        val userEmail = auth.currentUser?.email ?: "Anonymous"
        val userId = auth.currentUser?.uid ?: return

        // Create an order map to save in Realtime Database
        val order = hashMapOf(
            "userEmail" to userEmail,
            "address" to address,
            "phoneNumber" to phoneNumber,
            "cartItems" to cartItems.map { item ->
                mapOf(
                    "productName" to item.title,
                    "quantity" to item.quantity
                )
            }
        )

        // Save order to Realtime Database under 'Orders' node
        db.child("Orders").child(userId).push().setValue(order)
            .addOnSuccessListener {
                // Show success message
                Toast.makeText(context, "Your order has been placed successfully!", Toast.LENGTH_SHORT).show()

                // Navigate to the Home fragment
                findNavController().navigate(R.id.action_orderplaceFragment_to_newHome_Fragment)

                // Clear the cart after placing the order
                clearCart(userId)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to place order: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearCart(userId: String) {
        db.child("Cart").child(userId).removeValue()
            .addOnSuccessListener {
                Log.d("OrderplaceFragment", "Cart cleared successfully")
            }
            .addOnFailureListener { e ->
                Log.e("OrderplaceFragment", "Failed to clear cart: ${e.message}")
            }
    }
}
