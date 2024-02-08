package com.example.roomform.fragments.signup

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.roomform.R
import com.example.roomform.data.User
import com.example.roomform.data.UserDatabase
import com.example.roomform.databinding.FragmentSignUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val regExp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    private val passwordMatcher = Regex(regExp)
    private lateinit var database: UserDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        database = UserDatabase.getDatabase(requireContext())

        binding.clickHereToSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.signup.setOnClickListener {
            val phNumber = binding.phoneNumber.text.toString()
            val name = binding.name.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (phNumber.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                handleEmptyFields(phNumber, name, password, confirmPassword)
            } else if (!password.matches(passwordMatcher)) {
                binding.password.error = "Password must have at least 1 Uppercase, 1 Lowercase, 1 Special character, 1 Special symbol."
            } else if (password != confirmPassword) {
                binding.confirmPassword.error = "Passwords do not match."
            } else {
                handleSignUp(phNumber, name, password)
            }
        }
        return binding.root
    }

    private fun handleEmptyFields(phNumber: String, name: String, password: String, confirmPassword: String) {
        if (phNumber.isEmpty()) binding.phoneNumber.error = "Field cannot be empty"
        if (name.isEmpty()) binding.name.error = "Name cannot be empty"
        if (password.isEmpty()) binding.password.error = "Password cannot be empty"
        if (confirmPassword.isEmpty()) binding.confirmPassword.error = "Confirm password cannot be empty"

        if (phNumber.length == 10) {
            Log.e("MainActivity", "Valid")
        } else {
            binding.phoneNumber.error = "Invalid number"
        }
    }

    private fun handleSignUp(phNumber: String, name: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (checkPhoneNumber(phNumber)) {
                    withContext(Dispatchers.Main) {
                        binding.phoneNumber.error = "Phone number already registered. Try a different number."
                    }
                } else {
                    if (inputCheck(phNumber, name, password)) {
                        val user = User(phNumber, name, password)
                        database.userDao().insertUser(user)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Account Created", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun inputCheck(
        phoneNumber: String,
        name: String,
        password: String
    ): Boolean {
        return !(phoneNumber.isEmpty() || name.isEmpty() || password.isEmpty())
    }

    private suspend fun checkPhoneNumber(phoneNumber: String): Boolean {
        return database.userDao().getUserByPhoneNumber(phoneNumber) != null
    }
}
