package com.example.gurudev

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gurudev.databinding.LoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {

    private var _binding: LoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        // Redirect to home if user is already logged in
        firebaseAuth.currentUser?.let {
            goToHomepage()
            return
        }

        binding.textViewSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, SignupFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (isValidInput(email, password)) {
                loginUser(email, password)
            }
        }
    }

    private fun isValidInput(email: String, password: String): Boolean {
        return when {
            email.isEmpty() || password.isEmpty() -> {
                showToast("Empty fields are not allowed")
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showToast("Please enter a valid email address")
                false
            }
            password.length < 6 -> {
                showToast("Password must be at least 6 characters")
                false
            }
            else -> true
        }
    }

    private fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Log.d("Login", "Logged in as: ${user?.email}, Name: ${user?.displayName}")

                    if (user?.displayName == null) {
                        if (user != null) {
                            fetchUserNameFromFirestore(user.uid)
                        }
                    } else {
                        goToHomepage()
                    }
                } else {
                    Log.e("Login", "Login failed: ${task.exception?.message}")
                    showToast("Login failed: ${task.exception?.message}")
                }
            }
    }

    private fun fetchUserNameFromFirestore(uid: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (!isAdded) return@addOnSuccessListener

                val name = document.getString("name") ?: "User"
                Log.d("Firestore", "Fetched name: $name")

                val user = firebaseAuth.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()

                user?.updateProfile(profileUpdates)?.addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        Log.d("Profile Update", "User profile updated with name: $name")
                    }
                }

                goToHomepage()
            }
            .addOnFailureListener { e ->
                if (!isAdded) return@addOnFailureListener
                Log.e("Firestore", "Error fetching user name", e)
                goToHomepage()
            }
    }

    private fun goToHomepage() {
        if (isAdded && activity != null) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, Homefragment()) // Ensure HomeFragment exists
                .commit()
        } else {
            Log.e("Fragment Error", "goToHomepage() called after fragment was detached")
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
