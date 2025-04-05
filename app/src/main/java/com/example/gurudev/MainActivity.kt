package com.example.gurudev

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import com.example.gurudev.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseApp (Important to prevent crash)
        FirebaseApp.initializeApp(this)

        firebaseAuth = FirebaseAuth.getInstance()

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        // Set up Drawer Toggle
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Handle Navigation Item Clicks
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(Homefragment())
                R.id.nav_community -> loadFragment(CommunityFragment())
                R.id.nav_settings -> loadFragment(SettingsFragment())
                R.id.nav_Profile -> loadFragment(ProfileFragment())
                R.id.nav_contact -> loadFragment(ContactFragment())
                R.id.nav_shivir -> loadFragment(ShivirFragment())
                R.id.nav_donate  -> loadFragment(DonateFragment())
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Check if user is logged in, and load the correct fragment
        if (savedInstanceState == null) {
            if (firebaseAuth.currentUser != null) {
                loadFragment(Homefragment()) // If logged in
            } else {
                loadFragment(LoginFragment()) // If not logged in
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
