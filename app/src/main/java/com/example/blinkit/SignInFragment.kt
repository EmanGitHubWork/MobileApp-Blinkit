package com.example.blinkit

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.blinkit.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        setStatusBarColor()
        getUserNumber()
        onContinueButtonClick()
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.etUserNumber.requestFocus()
        showKeyboard()
    }
    private fun onContinueButtonClick() {
        binding.btncontinue.setOnClickListener {
            val number = binding.etUserNumber.text.toString()
            if (number.isEmpty() || number.length != 10) {
                Utils.showToast(requireContext(), "Please enter a valid phone number") // Corrected here
            } else {
                val bundle = Bundle()
                bundle.putString("number", number)
                findNavController().navigate(R.id.action_signInFragment_to_OTP_Fragment, bundle)
            }
        }
    }

    private fun getUserNumber() {
        binding.etUserNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do something before text changes
            }

            override fun onTextChanged(number: CharSequence?, start: Int, before: Int, count: Int) {
                val len = number?.length
                if (len == 10) {
                    binding.btncontinue.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.green)
                    )
                } else {
                    binding.btncontinue.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.button_background)
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do something after text changes
            }
        })
    }

    private fun showKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
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
