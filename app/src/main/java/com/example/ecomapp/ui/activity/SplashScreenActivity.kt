package com.example.ecomapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import com.example.ecomapp.R
import com.example.ecomapp.databinding.ActivitySplashScreenBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_anim)
        binding.ivSplashLogo.startAnimation(animation)

        Handler(Looper.getMainLooper()).postDelayed({
            Log.i("Splash", "SplashScreen Loaded")
            startActivity(Intent(this, MainActivity::class.java))
            finish()}, 2000)

    }
}