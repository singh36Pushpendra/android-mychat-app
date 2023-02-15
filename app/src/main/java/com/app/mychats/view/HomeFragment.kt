package com.app.mychats.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.mychats.R
import com.app.mychats.model.PageAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val viewPagerHome = view.viewPagerHome
        viewPagerHome.adapter = PageAdapter(requireActivity().supportFragmentManager)
        view.tabLayoutHome.setupWithViewPager(viewPagerHome)
        return view
    }

}