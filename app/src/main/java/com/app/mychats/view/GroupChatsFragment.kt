package com.app.mychats.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mychats.R
import com.app.mychats.model.User
import com.app.mychats.model.UsersAdapter
import kotlinx.android.synthetic.main.fragment_chat_groups.view.*

class GroupChatsFragment : Fragment() {

    private lateinit var contactList: MutableList<User>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat_groups, container, false)
        contactList = mutableListOf()
        contactList.add(User("", "Friends Group", "",
        "friend.png"))
        val usersAdapter = UsersAdapter(requireContext(), contactList, true)
        val groupRecyclerView = view.groupRecyclerView
        groupRecyclerView.adapter = usersAdapter
        groupRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

}