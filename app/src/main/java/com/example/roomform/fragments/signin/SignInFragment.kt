package com.example.roomform.fragments.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.roomform.R
import com.example.roomform.data.UserDatabase
import com.example.roomform.databinding.FragmentSignInBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var database: UserDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        database = UserDatabase.getDatabase(requireContext())

        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.signInBtn.setOnClickListener {
            val phoneNumber = binding.signInPhoneNumber.text.toString()
            val password = binding.signInPassword.text.toString()

            if(phoneNumber.isEmpty()) binding.signInPhoneNumber.error = "phone number cannot be empty"
            if(password.isEmpty()) binding.signInPassword.error = "password cannot be empty"

            if(phoneNumber.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = database.userDao().signIn(phoneNumber , password)
                    withContext(Dispatchers.Main) {
                        if(user != null) {
                            // nav
                            findNavController().navigate(R.id.action_signInFragment_to_welcomeFragment)
                            Toast.makeText(requireContext(),"Sign-In successful",Toast.LENGTH_SHORT).show()
                        } else {
                            binding.signInPhoneNumber.error = "Invalid Number"
                            binding.signInPassword.error = "Invalid password"
                        }
                    }
                }
            }
        }
        return binding.root
    }
}