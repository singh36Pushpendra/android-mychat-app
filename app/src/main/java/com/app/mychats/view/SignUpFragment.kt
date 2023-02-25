package com.app.mychats.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.mychats.R
import com.app.mychats.model.User
import com.app.mychats.util.MyChatUtil
import com.app.mychats.viewmodel.SignUpViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import java.util.concurrent.TimeUnit

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

//    // Getting FirebaseAuth instance.
//    private lateinit var mAuth: FirebaseAuth

    private lateinit var progressBarSignUp: ProgressBar
    private lateinit var btnGetOTP: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
//
//        signUpViewModel = ViewModelProvider(
//            this,
//            SignUpViewModelFactory(UserAuthService(requireContext()))
//        )[SignUpViewModel::class.java]

        val etName = view.etName
        val etNumber = view.etNumber
        progressBarSignUp = view.progressBarSignUp
        btnGetOTP = view.btnGetOTP

//        mAuth = FirebaseAuth.getInstance()
        view.findViewById<Button>(R.id.btnGetOTP).setOnClickListener {

            progressBarSignUp.visibility = View.VISIBLE
            btnGetOTP.visibility = View.INVISIBLE

            val user = User(
                FirebaseAuth.getInstance().currentUser!!.uid,
                etName.text.toString(), etNumber.text.toString())
            Toast.makeText(context, "It's working", Toast.LENGTH_SHORT).show()
            val verifyOtpFragment = VerifyOtpFragment()
            val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber("+91${user.phoneNumber}")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(context as Activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                        progressBarSignUp.visibility = View.GONE
                        btnGetOTP.visibility = View.VISIBLE
                    }

                    override fun onVerificationFailed(e: FirebaseException) {

                        progressBarSignUp.visibility = View.GONE
                        btnGetOTP.visibility = View.VISIBLE
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onCodeSent(backendOTP: String, p1: PhoneAuthProvider.ForceResendingToken) {

                        progressBarSignUp.visibility = View.GONE
                        btnGetOTP.visibility = View.VISIBLE

                        val bundle = Bundle()
                        bundle.putString("name", user.name)
                        bundle.putString("phoneNumber", user.phoneNumber)
                        bundle.putString("backendOTP", backendOTP)
                        verifyOtpFragment.arguments = bundle
                        MyChatUtil.replaceFragment(activity, verifyOtpFragment)
                    }
                })
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)

        }

//        signUpViewModel.signUpStatus.observe(viewLifecycleOwner) {
//            if (it.status) {
//                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                MyChatUtil.replaceFragment(activity, VerifyOtpFragment())
//            }
//            else {
//                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//            }
//        }
        return view
    }

}