package com.example.ecomapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.ecomapp.R
import com.example.ecomapp.databinding.ActivityMainBinding
import com.example.ecomapp.vm.AuthViewModel
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        /*binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)*/
        //binding.etUsername.text =  SpannableStringBuilder("Yahya123")
        //binding.etPassword.text =  SpannableStringBuilder("TestingPassword123")
        setThemeFromPreferences()
        init()
    }

    private fun setThemeFromPreferences() {
        val sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE)
        val isThemeDark = sharedPreferences.getBoolean("isDark", false)

        if(isThemeDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun init() {
        //   viewModel= ViewModelProvider(this)[signInViewModel::class.java]
        bindViewModel()
        eventActionClickListener()
    }

    private fun bindViewModel() {
        viewModel.logInUsername.observe(this) {
            binding.etUsername.text = SpannableStringBuilder(it)
        }
        viewModel.logInPassword.observe(this) {
            binding.etPassword.text = SpannableStringBuilder(it)
        }
        viewModel.logInUsernameError.observe(this) {
            binding.etUsernameContainer.error = SpannableStringBuilder(it)
        }
        viewModel.logInPasswordError.observe(this) {
            binding.etPasswordContainer.error = SpannableStringBuilder(it)
        }
    }

    private fun eventActionClickListener() {
        binding.btSignIn.setOnClickListener {
            if (viewModel.validateLoginInfo(binding.etUsername.text.toString(), binding.etPassword.text.toString())) {
                Log.i("signIn", "Success")
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Log.i("signin", "Failure")
            }
        }
        binding.tvSignUp.setOnClickListener{
            startActivity(Intent(this,RegistrationActivity::class.java))
        }

//        binding.etUsername.doOnTextChanged { text, start, before, count -> viewModel.onLogInUsernameChange(text.toString())  }
//        binding.etPassword.doOnTextChanged { text, start, before, count -> viewModel.onLogInPasswordChange(text.toString())  }
    }

    private fun validateFields(): Boolean {
        val username : String = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        var usernameError = ""
        var passwordError = ""
        var fieldsAreValid = true
        val condition = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)

        val p = Pattern.compile(
            "[^a-z0-9 ]", Pattern.CASE_INSENSITIVE
        )
        val m = p.matcher(username)

        if (username.isEmpty()) {
            fieldsAreValid = false
            usernameError = getString(R.string.username_cannot_be_empty)
        }
        else if (condition.matcher(username).find()) {
            fieldsAreValid = false
            usernameError = getString(R.string.username_cannot_contain_special_characters)
        } else if (username.length < 3) {
            fieldsAreValid = false
            usernameError = getString(R.string.username_must_be_atleast_3_characters)
        }


        if (password.isEmpty()) {
            fieldsAreValid = false
            passwordError = getString(R.string.password_cannot_be_empty)
        } else if (
            !password.matches(".*[A-Z].*".toRegex()) ||
            !password.matches(".*[a-z].*".toRegex()) ||
            !password.matches(".*[0-9].*".toRegex())
        ) {
            fieldsAreValid = false
            passwordError = getString(R.string.password_conditions)
        } else if (password.length < 8) {
            fieldsAreValid = false
            passwordError = getString(R.string.password_must_be_atleast_8_characters)
        }

        if( usernameError.isNotEmpty() ) binding.etUsernameContainer.error = usernameError
        if ( passwordError.isNotEmpty() ) binding.etPasswordContainer.error = passwordError
        return fieldsAreValid
    }

}