package com.example.blinkit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.blinkit.databinding.FragmentOTPBinding

class OTP_Fragment : Fragment() {
    private lateinit var binding: FragmentOTPBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOTPBinding.inflate(inflater, container, false)
        onLoginButtonClick()
        return binding.root
    }

    private fun onLoginButtonClick() {
        binding.btnLogin.setOnClickListener {
            val number = binding.etOtp1.text.toString()
            if (number.isNotEmpty()) {
                val bundle = Bundle()
                bundle.putString("number", number)
                findNavController().navigate(R.id.action_OTP_Fragment_to_newHome_Fragment, bundle)
            }
        }
    }
}
