package com.app.mychats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.mychats.model.AuthOTPListener
import com.app.mychats.model.UserAuthService

class VerifyOtpViewModel(val userAuthService: UserAuthService): ViewModel() {
    private val _verifyOTPStatus = MutableLiveData<AuthOTPListener>()
    val verifyOTPStatus: LiveData<AuthOTPListener> = _verifyOTPStatus

    fun verifyCode(code: String) {
        userAuthService.verifyCode(code) {
            _verifyOTPStatus.value = it
        }
    }
}