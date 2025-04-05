package com.example.gurudev

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gurudev.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonSignup.setOnClickListener {
            val name = binding.edittextName.text.toString().trim()
            val email = binding.edittextEmail.text.toString().trim()
            val password = binding.edittextPassword.text.toString().trim()
            val confirmPassword = binding.edittextConfirmPass.text.toString().trim()

            if (validateInput(name, email, password, confirmPassword)) {
                registerUser(email, password, name)
            }
        }

        binding.textViewSignIn.setOnClickListener {
            goToLogin()
        }
    }

    private fun validateInput(name: String, email: String, password: String, confirmPassword: String): Boolean {
        return when {
            name.isEmpty() -> {
                showToast("Name cannot be empty")
                false
            }
            email.isEmpty() -> {
                showToast("Email cannot be empty")
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showToast("Invalid email format")
                false
            }
            password.length < 6 -> {
                showToast("Password must be at least 6 characters")
                false
            }
            password != confirmPassword -> {
                showToast("Passwords do not match")
                false
            }
            else -> true
        }
    }

    private fun registerUser(email: String, password: String, name: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser


                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast("Signup successful!")
                            goToLogin()
                        }
                    }
                } else {
                    showToast(task.exception?.localizedMessage ?: "Registration failed")
                }
            }
    }

    private fun goToLogin() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment()) // Ensure LoginFragment exists
            .commitAllowingStateLoss()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
