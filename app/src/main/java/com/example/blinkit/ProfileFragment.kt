package com.example.blinkit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.blinkit.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.blinkit.models.Order
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        // Fetch user data for profile
        fetchUserProfileData()

        // Handle "Your Orders" click
        binding.llOrders.setOnClickListener {
            fetchOrders()
        }

        // Handle "Log Out" click
        binding.llLogout.setOnClickListener {
            logOut()
        }

        return binding.root
    }

    // Fetch the user's profile data (like phone number, etc.) from Firebase
    private fun fetchUserProfileData() {
        val userId = auth.currentUser?.uid ?: return
        db.child("Users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val phoneNumber = snapshot.child("phoneNumber").getValue(String::class.java)
                binding.tvProfilePhoneNumber.text = phoneNumber ?: "Phone number not available"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch profile data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Fetch the user's orders from the Firebase Realtime Database
    // Fetch the user's orders from the Firebase Realtime Database
    private fun fetchOrders() {
        val userId = auth.currentUser?.uid ?: return
        db.child("Orders").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Here you can either display the orders in a RecyclerView or handle accordingly
                    val orders = mutableListOf<String>()
                    for (orderSnapshot in snapshot.children) {
                        val order = orderSnapshot.getValue(Order::class.java)
                        order?.let {
                            // Loop through the cart items and create a string showing product name and quantity
                            for (item in it.cartItems) {
                                orders.add("Product: ${item.productName}, Quantity: ${item.quantity}")
                            }
                        }
                    }
                    // Show the orders (for now, we'll just log them)
                    if (orders.isNotEmpty()) {
                        Toast.makeText(context, "Orders:\n${orders.joinToString("\n")}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "No orders found.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "No orders found.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch orders: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    // Log out the user and navigate to OTPFragment
    private fun logOut() {
        auth.signOut()
        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_profileFragment_to_otpFragment)
    }
}
