package com.app.mychats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.mychats.model.AuthOTPListener
import com.app.mychats.model.User
import com.app.mychats.model.UserAuthService

class SignUpViewModel(val userAuthService: UserAuthService): ViewModel() {
    private val _signUpStatus = MutableLiveData<AuthOTPListener>()
    val signUpStatus: LiveData<AuthOTPListener> = _signUpStatus

    fun performAuth(user: User) {
        userAuthService.performAuth(user) {
            _signUpStatus.value = it
        }
    }
}