package com.example.ecomapp.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.example.ecomapp.R
import com.example.ecomapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }


    private fun init() {
        setSwitchState()
        initListeners()
    }

    private fun setSwitchState() {
        val sharedPref = getSharedPreferences("ThemePref", MODE_PRIVATE)
        val isDark = sharedPref.getBoolean("isDark", false)

        binding.switchTheme.isChecked = isDark

    }

    private fun initListeners() {
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                val sharedPref = getSharedPreferences("ThemePref", MODE_PRIVATE)
                sharedPref.edit().apply {
                    putBoolean("isDark", true)
                    apply()
                }
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                val sharedPref = getSharedPreferences("ThemePref", MODE_PRIVATE)
                sharedPref.edit().apply {
                    putBoolean("isDark", false)
                    apply()
                }
            }
        }
    }
}