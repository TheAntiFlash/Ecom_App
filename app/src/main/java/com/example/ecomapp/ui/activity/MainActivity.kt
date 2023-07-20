package com.example.ecomapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomapp.R
import com.example.ecomapp.databinding.ActivityMainBinding
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //  lateinit var viewModel:signInViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        //   viewModel= ViewModelProvider(this)[signInViewModel::class.java]

        eventActionClickListener()
    }

    private fun eventActionClickListener() {
        binding.btSignIn.setOnClickListener {
            if (validateFields()) {
                Log.i("signIn", "Success")
                binding.etUsernameContainer.error = ""
                binding.etPasswordContainer.error = ""
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()

            } else {
                Log.i("signin", "Failure")
            }
        }
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