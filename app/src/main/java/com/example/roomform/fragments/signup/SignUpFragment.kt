package com.example.roomform.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.roomform.R
import com.example.roomform.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding : FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.clickHereToSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.signup.setOnClickListener {
            val phNumber = binding.phoneNumber.text.toString()
            val name = binding.name.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if(phNumber.isEmpty() && name.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                binding.phoneNumber.error = "Field cannot be empty"
                binding.name.error = "name cannot be empty"
                binding.password.error = "password cannot be empty"
                binding.confirmPassword.error = "confirm password cannot be empty"
            }
        }
        return binding.root
    }
}