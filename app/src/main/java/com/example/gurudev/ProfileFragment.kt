package com.example.gurudev

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gurudev.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        val user = firebaseAuth.currentUser

        if (user != null) {
            fetchUserData(user.uid)
        } else {
            binding.textViewName.text = "Not Logged In"
            binding.textViewEmail.text = ""
        }

        // Logout Button Click
        binding.buttonLogout.setOnClickListener {
            firebaseAuth.signOut()
            goToLogin()
        }

        // Delete Account Click
        binding.btnDeleteAccount.setOnClickListener {
            showDeleteAccountDialog()
        }

        // Change Password Click
        binding.buttonChangepassword.setOnClickListener {
            handleChangePassword()
        }

        // Social Media Click Listeners
        binding.facebook.setOnClickListener { openSocialMedia("https://www.facebook.com/Gayatriparivar/") }
        binding.instagram.setOnClickListener { openSocialMedia("https://www.instagram.com/awgpofficial/?hl=gu") }
        binding.twitter.setOnClickListener { openSocialMedia("https://x.com/awgpofficial") }
    }

    private fun fetchUserData(userId: String) {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val fullName = document.getString("fullName") ?: "Not Set"
                    val email = document.getString("email") ?: "Not Available"

                    binding.textViewName.text = fullName
                    binding.textViewEmail.text = email

                    Log.d("Firestore", "User data fetched: $fullName, $email")
                } else {
                    Log.e("Firestore", "User document does not exist")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching user data: ${e.message}")
            }
    }

    private fun goToLogin() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment())
            .commit()
    }

    private fun handleChangePassword() {
        val oldPassword = binding.textViewpassword.text.toString().trim()
        val newPassword = binding.textViewchangepassword.text.toString().trim()

        if ( newPassword.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter  new passwords", Toast.LENGTH_SHORT).show()
            return
        }

        val user = firebaseAuth.currentUser
        user?.updatePassword(newPassword)
            ?.addOnSuccessListener {
                Toast.makeText(requireContext(), "Password updated successfully!", Toast.LENGTH_SHORT).show()
            }
            ?.addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to update password: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showDeleteAccountDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ -> deleteAccount() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteAccount() {
        val user = firebaseAuth.currentUser
        user?.delete()
            ?.addOnSuccessListener {
                Toast.makeText(requireContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show()
                goToLogin()
            }
            ?.addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error deleting account: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun openSocialMedia(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Ensure it opens in a new task
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Unable to open link", Toast.LENGTH_SHORT).show()
            Log.e("ProfileFragment", "Error opening URL: ${e.message}")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}