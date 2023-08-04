package com.example.ecomapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecomapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern


class AuthViewModel : ViewModel() {

    private val _logInUsername = MutableLiveData<String>("")
    private val _logInPassword = MutableLiveData<String>("")
    private val _logInUsernameError = MutableLiveData("")
    private val _logInPasswordError = MutableLiveData("")

    val logInUsername : LiveData<String>
        get() = _logInUsername
    val logInPassword : LiveData<String>
        get() = _logInPassword
    val logInUsernameError : LiveData<String>
        get() = _logInUsernameError
    val logInPasswordError : LiveData<String>
        get() = _logInPasswordError

    fun onLogInUsernameChange(username: String)  { _logInUsername.value = username }
    fun onLogInPasswordChange(password: String)  { _logInPassword.value = password }

    fun validateLoginInfo(username: String, password: String): Boolean {
        _logInUsername.value = username
        _logInPassword.value = password
        var fieldsAreValid = true
        val condition = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)

        val p = Pattern.compile(
            "[^a-z0-9 ]", Pattern.CASE_INSENSITIVE
        )
        val m = logInUsername.value?.let { p.matcher(it) }

        if (logInUsername.value?.isEmpty() == true) {
            fieldsAreValid = false
            _logInUsernameError.value = "username cannot be empty."
        }
        else if (logInUsername.value?.let { condition.matcher(it).find() } == true) {
            fieldsAreValid = false
            _logInUsernameError.value = "username cannot contain special characters."
        } else if (_logInUsername.value!!.length < 3) {
            fieldsAreValid = false
            _logInUsernameError.value = "username must be at least 3 characters."
        }
        else {
            _logInUsernameError.value = ""
        }


        if (logInPassword.value?.isEmpty() == true) {
            fieldsAreValid = false
            _logInPasswordError.value = "password cannot be empty."
        } else if (
            !logInPassword.value!!.matches(".*[A-Z].*".toRegex()) ||
            !logInPassword.value!!.matches(".*[a-z].*".toRegex()) ||
            !logInPassword.value!!.matches(".*[0-9].*".toRegex())
        ) {
            fieldsAreValid = false
            _logInPasswordError.value = "password must contain at least \n1 Uppercase \n1 Lowercase \n1 number."
        } else if (logInPassword.value!!.length < 8) {
            fieldsAreValid = false
            _logInPasswordError.value = "password must be at least 8 characters"
        }
        else {
            _logInPasswordError.value = ""
        }

        return fieldsAreValid
    }


}