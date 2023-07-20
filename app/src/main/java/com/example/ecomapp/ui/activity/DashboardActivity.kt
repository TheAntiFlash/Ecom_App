package com.example.ecomapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.example.ecomapp.R
import com.example.ecomapp.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var binding : ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init() {
        drawerLayout = binding.mainDrawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        supportActionBar?.setIcon(R.drawable.baseline_menu_24)
        initListeners()
    }

    private fun initListeners() {
        // Nav Drawer
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        setSupportActionBar(binding.toolbarNav)
        /*binding.navigationDrawer.setNavigationItemSelectedListener {
            handleBottomNavigation(
                it.itemId
            )
        }*/


        // Bottom nav
        binding.bottomNavigationView.setOnItemSelectedListener {
            handleBottomNavigation(
                it.itemId
            )
        }
        binding.bottomNavigationView.selectedItemId = R.id.home
    }

    private fun handleBottomNavigation(
        menuItemId: Int
    ): Boolean {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHost.navController
       return when(menuItemId) {
            R.id.home -> {
                navController.navigate(R.id.homeFragment)
                true
            }
            R.id.person -> {
                navController.navigate(R.id.profileFragment)
                true
            }
            R.id.chat -> {
                navController.navigate(R.id.exampleFragment)
                true
            }
            else -> false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

}
