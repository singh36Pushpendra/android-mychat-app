package com.app.mychats.view

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import com.app.mychats.R
import com.app.mychats.model.PageAdapter
import com.app.mychats.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.io.File

class HomeFragment : Fragment() {

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        var user: User
        val profile: MenuItem = view.topAppBar.menu.findItem(R.id.profile)
        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .get().addOnSuccessListener {
                user = it.getValue(User::class.java)!!


            }


        view.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.profile -> {
                    ProfileDialogFragment().show(
                        activity?.supportFragmentManager!!,
                        "Profile Dialog"
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }
        val viewPagerHome = view.viewPagerHome
        viewPagerHome.adapter = PageAdapter(requireActivity().supportFragmentManager, arguments ?: Bundle())
        view.tabLayoutHome.setupWithViewPager(viewPagerHome)
        return view
    }

}