package com.app.mychats.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.mychats.R
import com.app.mychats.model.UserAuthService
import com.app.mychats.util.MyChatUtil
import com.app.mychats.viewmodel.VerifyOtpViewModel
import com.app.mychats.viewmodel.factory.VerifyOtpViewModelFactory
import kotlinx.android.synthetic.main.fragment_verify_otp.view.*

class VerifyOtpFragment : Fragment() {

    private lateinit var verifyOtpViewModel: VerifyOtpViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_verify_otp, container, false)

        verifyOtpViewModel = ViewModelProvider(
            this,
            VerifyOtpViewModelFactory(UserAuthService(requireActivity()))
        )[VerifyOtpViewModel::class.java]

        view.btnVerifyOTP.setOnClickListener {
            val code = view.etOTP.text.toString()
            verifyOtpViewModel.verifyCode(code)
        }

        verifyOtpViewModel.verifyOTPStatus.observe(viewLifecycleOwner) {
            if (it.status) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                MyChatUtil.replaceFragment(activity, HomeFragment())
            }
            else {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

}