package com.app.mychats.model

data class AuthOTPListener(val status: Boolean, val message: String, val otp: String? = null)
