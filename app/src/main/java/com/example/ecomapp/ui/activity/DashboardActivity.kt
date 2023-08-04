package com.example.ecomapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.example.ecomapp.R
import com.example.ecomapp.databinding.ActivityDashboardBinding
import com.google.android.material.navigation.NavigationView

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init() {
        drawerLayout = binding.mainDrawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
       drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initListeners()
      //  supportActionBar?.setIcon(R.drawable.baseline_menu_24)

    }

    private fun initListeners() {
        // Nav Drawer
//        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()



        setSupportActionBar(binding.toolbarNav)

        binding.navigationDrawer.setNavigationItemSelectedListener {
            handleDrawerNavigation(it.itemId)

        }

        // Bottom nav
        binding.bottomNavigationView.setOnItemSelectedListener {
            handleBottomNavigation(it.itemId)
        }
        binding.bottomNavigationView.selectedItemId = R.id.home_nav_item
    }


    private fun handleDrawerNavigation(itemId: Int): Boolean {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHost.navController
        return when (itemId) {

            R.id.profile_nav_item -> {
                navController.navigate(R.id.profileFragment)
                true
            }
            R.id.addItem_nav_item -> {
                startActivity(Intent(this, AddItemActivity::class.java))
                true
            }
            R.id.settings_nav_item -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            else -> false
        }
    }

    private fun handleBottomNavigation(
        menuItemId: Int
    ): Boolean {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHost.navController
        return when (menuItemId) {
            R.id.home_nav_item -> {
                navController.navigate(R.id.homeFragment)
                true
            }

            R.id.chat_nav_item -> {
                navController.navigate(R.id.exampleFragment)
                true
            }

            R.id.cart_nav_item -> {
                navController.navigate(R.id.cartFragment)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //showMessage(item.itemId)
        return  true
    }

}
