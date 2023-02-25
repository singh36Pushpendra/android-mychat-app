package com.app.mychats.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.mychats.R
import com.app.mychats.model.User
import com.app.mychats.model.UserAuthService
import com.app.mychats.util.MyChatUtil
import com.app.mychats.viewmodel.VerifyOtpViewModel
import com.app.mychats.viewmodel.factory.VerifyOtpViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_verify_otp.view.*

class VerifyOtpFragment : Fragment() {

    private lateinit var verifyOtpViewModel: VerifyOtpViewModel

    private lateinit var getOTPBackend: String
    private lateinit var phoneNumber: String
    private lateinit var name: String

    private lateinit var progressBarVerifyOTP: ProgressBar
    private lateinit var btnVerifyOTP: Button

    private lateinit var userRef: DatabaseReference
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_verify_otp, container, false)

//        verifyOtpViewModel = ViewModelProvider(
//            this,
//            VerifyOtpViewModelFactory(UserAuthService(requireActivity()))
//        )[VerifyOtpViewModel::class.java]

        getOTPBackend = arguments?.getString("backendOTP").toString()
        name = arguments?.getString("name").toString()
        phoneNumber = arguments?.getString("phoneNumber").toString()


        btnVerifyOTP = view.btnVerifyOTP
        progressBarVerifyOTP = view.progressBarVerifyOTP

        btnVerifyOTP.setOnClickListener {
            val code = view.etOTP.text.toString()
            progressBarVerifyOTP.visibility = View.VISIBLE
            btnVerifyOTP.visibility = View.INVISIBLE

            userRef = FirebaseDatabase.getInstance().getReference("users")
            userId = userRef.push().key.toString()
            val user = User(userId, name, phoneNumber)
            val phoneAuthCredential = PhoneAuthProvider.getCredential(getOTPBackend, code)
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener {

                    progressBarVerifyOTP.visibility = View.GONE
                    btnVerifyOTP.visibility = View.VISIBLE
                    if (it.isSuccessful) {
                        saveUser(user)
                        MyChatUtil.replaceFragment(requireActivity(), HomeFragment())
                    }
                }
        }

//        verifyOtpViewModel.verifyOTPStatus.observe(viewLifecycleOwner) {
//            if (it.status) {
//                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                MyChatUtil.replaceFragment(activity, HomeFragment())
//            }
//            else {
//                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//            }
//        }
        return view
    }

    private fun saveUser(user: User) {
        userRef.child(userId).setValue(user)
    }

}