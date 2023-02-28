package com.app.mychats.model

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class UserAuthService(private val context: Context) {
    private lateinit var verificationId: String

    // Getting FirebaseAuth instance.
    private var mAuth = FirebaseAuth.getInstance()

    fun performAuth(user: User, listener: (AuthOTPListener) -> Unit) {
        if (user.phoneNumber.isEmpty())
            listener(AuthOTPListener(false, "Please enter a valid phone number."))
        else {
            val phone = "+91${user.phoneNumber}"
            sendVerificationCode(phone)
            listener(AuthOTPListener(true, "Valid phone number."))
        }
    }

    private fun sendVerificationCode(phone: String) {
        // This method is used for getting OTP on user phone number.
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(context as Activity)
            .setCallbacks(mCallBack)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    // callback method is called on Phone auth provider.
    private val   // initializing our callbacks for on
    // verification callback method.
            mCallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s
            }

            // this method is called when user
            // receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP.


                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.
//                    verifyCode(code)
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                // displaying error message with firebase exception.
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }

    fun verifyCode(code: String, listener: (AuthOTPListener) -> Unit) {
        // below line is used for getting 
        // credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential, listener)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential, listener: (AuthOTPListener) -> Unit) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    listener(AuthOTPListener(true, "You are Logged In!"))
                } else {
                    // if the code is not correct then we are
                    // transfering an error message to the user.
                    listener(AuthOTPListener(false, "${task.exception?.message}"))
                }
            }
    }
}