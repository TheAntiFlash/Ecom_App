package com.example.ecomapp.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class signInViewModel : ViewModel() {

    val username = MutableLiveData("")
    val password = MutableLiveData<String>()
    val usernameError = MutableLiveData("")
    val passwordError= MutableLiveData("")

    fun signIn() {
        validateFields()
    }

    fun validateFields() : Boolean {
        var fieldsAreValid = true
        val condition = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        if(username.value != null) {

            if (username.value!!.isEmpty()) {
                fieldsAreValid = false
                usernameError.value = "Username cannot be empty."
            } else if (!condition.matcher(username.value!!).find()) {
                fieldsAreValid = false
                usernameError.value = "Username cannot contain special characters."
            }
            else if (username.value!!.length < 8) {
                fieldsAreValid = false
                usernameError.value = "Username must be atLeast 3 characters."
            }

        }
        else {
            usernameError.value = "Username cannot be empty."
            fieldsAreValid = false
        }

        if(password.value != null) {

            if (password.value!!.isEmpty()) {
                fieldsAreValid = false
                passwordError.value = "Password cannot be empty."
            } else if (
                !password.value!!.matches(".*[A-Z].*".toRegex()) &&
                !password.value!!.matches(".*[a-z].*".toRegex()) &&
                !password.value!!.matches(".*[0-9].*".toRegex())
                    ) {
                fieldsAreValid = false
                passwordError.value = "Password must contain at least \n1 Uppercase \n1 Lowercase \n1 number."
            }
            else if (password.value!!.length < 8) {
                fieldsAreValid = false
                passwordError.value = "Username must be atLeast 3 characters."
            }

        }
        else {
            fieldsAreValid = false
        }
        return fieldsAreValid
    }
}