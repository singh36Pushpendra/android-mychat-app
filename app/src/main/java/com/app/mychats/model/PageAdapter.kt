package com.app.mychats.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.mychats.view.OneToOneChatsFragment
import com.app.mychats.view.GroupChatsFragment

class PageAdapter(fragmentManager: FragmentManager,val bundle: Bundle) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Chats"
            1 -> "GroupChats"
            else -> "Chats"
        }
    }
    override fun getItem(position: Int): Fragment {
        val oneToOneChatsFragment = OneToOneChatsFragment()
        oneToOneChatsFragment.arguments = bundle
        return when (position) {
            0 -> oneToOneChatsFragment
            1 -> GroupChatsFragment()
            else -> OneToOneChatsFragment()
        }
    }
}