package com.app.mychats.view

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.app.mychats.R
import com.app.mychats.model.User
import com.app.mychats.util.MyChatUtil
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.btnGetOTP
import kotlinx.android.synthetic.main.fragment_sign_in.view.etNumber
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import java.util.concurrent.TimeUnit

class SignInFragment : Fragment() {

    private lateinit var progressBarSignIn: ProgressBar
    private lateinit var btnGetOTP: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        val etNumber = view.etNumber
        progressBarSignIn = view.progressBarSignIn
        btnGetOTP = view.btnGetOTP

//        mAuth = FirebaseAuth.getInstance()
        view.findViewById<Button>(R.id.btnGetOTP).setOnClickListener {

            progressBarSignIn.visibility = View.VISIBLE
            btnGetOTP.visibility = View.INVISIBLE

            val phoneNumber = etNumber.text.toString()
//            val user = User(
//                FirebaseAuth.getInstance().currentUser!!.uid,
//                "", etNumber.text.toString()
//            )
            Toast.makeText(context, "It's working", Toast.LENGTH_SHORT).show()
            val verifyOtpFragment = VerifyOtpFragment()
            val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber("+91${phoneNumber}")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(context as Activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                        progressBarSignIn.visibility = View.GONE
                        btnGetOTP.visibility = View.VISIBLE
                    }

                    override fun onVerificationFailed(e: FirebaseException) {

                        progressBarSignIn.visibility = View.GONE
                        btnGetOTP.visibility = View.VISIBLE
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onCodeSent(
                        backendOTP: String,
                        p1: PhoneAuthProvider.ForceResendingToken
                    ) {

                        progressBarSignIn.visibility = View.GONE
                        btnGetOTP.visibility = View.VISIBLE

                        val bundle = Bundle()
                        bundle.putString("phoneNumber", phoneNumber)
                        bundle.putString("backendOTP", backendOTP)
                        verifyOtpFragment.arguments = bundle
                        MyChatUtil.replaceFragment(activity, verifyOtpFragment)
                    }
                })
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        view.btnSignUp.setOnClickListener {
            MyChatUtil.replaceFragment(activity, SignUpFragment())
        }
        return view
    }

}