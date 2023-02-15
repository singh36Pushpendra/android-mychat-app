package com.app.mychats.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.mychats.model.UserAuthService
import com.app.mychats.viewmodel.VerifyOtpViewModel

class VerifyOtpViewModelFactory(val userAuthService: UserAuthService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VerifyOtpViewModel(userAuthService) as T
    }
}