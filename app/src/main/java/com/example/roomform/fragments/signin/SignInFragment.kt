package com.example.roomform.fragments.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.roomform.R
import com.example.roomform.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.signInBtn.setOnClickListener {
            val phoneNumber = binding.signInPhoneNumber.text.toString()
            val password = binding.signInPassword.text.toString()

            if(phoneNumber.isEmpty() && password.isEmpty()) {
                binding.signInPhoneNumber.error = "phone number cannot be empty"
                binding.signInPassword.error = "password cannot be empty"
            }
        }
        return binding.root
    }
}