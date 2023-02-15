package com.app.mychats.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.mychats.view.ChatsFragment
import com.app.mychats.view.GroupChatsFragment

class PageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
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
        return when (position) {
            0 -> ChatsFragment()
            1 -> GroupChatsFragment()
            else -> ChatsFragment()
        }
    }
}