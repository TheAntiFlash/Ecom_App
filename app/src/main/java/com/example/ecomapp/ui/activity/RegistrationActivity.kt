package com.example.ecomapp.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toIcon
import com.example.ecomapp.R
import com.example.ecomapp.databinding.ActivityRegistrationBinding
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrationBinding
    private lateinit var pickMedia : ActivityResultLauncher<PickVisualMediaRequest>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        init()
    }

    private fun init () {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if(it != null) {
                Log.i("MediaPicker", "uri is not null")
                binding.ivProfilePicture.setImageDrawable(it.toIcon().loadDrawable(this))
                binding.ivEditIcon.visibility = View.INVISIBLE
            }
            else {
                Log.i("MediaPicker", "Error loading image")
            }
        }

        setEventListeners()
    }

    private fun setEventListeners() {
        binding.btSignUp.setOnClickListener {
            if (validateFields()) {
                Log.i("signUp", "Success")
                binding.etUsernameContainer.error = ""
                binding.etPasswordContainer.error = ""
                binding.etEmailContainer.error = ""
                binding.etConfirmPasswordContainer.error = ""
            } else {
                Log.i("signUp", "Failure")
            }
        }

        binding.flProfilePictureFrame.setOnClickListener {
            Log.d("FrameCheck", "Frame was clicked")
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED && checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED){
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_IMAGES)
                requestPermissions(permissions, 1001);
            }
            else{
                pickImageFromGallery()
            }

            Log.i("Permissions", "${checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED} ${checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED}")
        }
    }


    private fun validateFields(): Boolean {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        var emailError = ""
        var confirmPasswordError = ""
        var usernameError = ""
        var passwordError = ""
        var fieldsAreValid = true
        val condition = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)


        if (username.isEmpty()) {
            fieldsAreValid = false
            usernameError = getString(R.string.username_cannot_be_empty)
        } else if (condition.matcher(username).find()) {
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
            !password.matches(".*[A-Z].*".toRegex()) &&
            !password.matches(".*[a-z].*".toRegex()) &&
            !password.matches(".*[0-9].*".toRegex())
        ) {
            fieldsAreValid = false
            passwordError = getString(R.string.password_conditions)
        } else if (password.length < 8) {
            fieldsAreValid = false
            passwordError = getString(R.string.password_must_be_atleast_8_characters)
        }

        if (confirmPassword.isEmpty()) {
            fieldsAreValid = false
            confirmPasswordError = getString(R.string.password_cannot_be_empty)
        } else if (confirmPassword != password) {
            fieldsAreValid = false
            confirmPasswordError = getString(R.string.password_is_not_same)
        }

        if (email.isEmpty()) {
            fieldsAreValid = false
            emailError = getString(R.string.email_cannot_be_empty)
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            fieldsAreValid = false
            emailError = getString(R.string.email_has_incorrect_format)
        }

        if( usernameError.isNotEmpty() ) binding.etUsernameContainer.error = usernameError
        if ( passwordError.isNotEmpty() ) binding.etPasswordContainer.error = passwordError
        if( emailError.isNotEmpty() ) binding.etEmailContainer.error = emailError
        if ( confirmPasswordError.isNotEmpty() ) binding.etConfirmPasswordContainer.error = confirmPasswordError
        return fieldsAreValid
    }

    private fun pickImageFromGallery() {

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


}