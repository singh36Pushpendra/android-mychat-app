package com.app.mychats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.mychats.R
import com.app.mychats.model.User
import com.app.mychats.model.UserAuthService
import com.app.mychats.util.MyChatUtil
import com.app.mychats.viewmodel.SignUpViewModel
import com.app.mychats.viewmodel.factory.SignUpViewModelFactory
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var signUpViewModel: SignUpViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        signUpViewModel = ViewModelProvider(
            this,
            SignUpViewModelFactory(UserAuthService(requireContext()))
        )[SignUpViewModel::class.java]

        val etName = view.etName
        val etNumber = view.etNumber

        view.findViewById<Button>(R.id.btnGetOTP).setOnClickListener {

            val user = User(etName.text.toString(), etNumber.text.toString())
            signUpViewModel.performAuth(user)
        }

        signUpViewModel.signUpStatus.observe(viewLifecycleOwner) {
            if (it.status) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                MyChatUtil.replaceFragment(activity, VerifyOtpFragment())
            }
            else {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

}