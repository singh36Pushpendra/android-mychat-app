package com.app.mychats.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.app.mychats.R

object MyChatUtil {

    fun replaceFragment(activity: FragmentActivity?, fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }

}