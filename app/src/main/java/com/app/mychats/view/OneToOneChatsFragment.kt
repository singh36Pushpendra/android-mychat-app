package com.app.mychats.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.mychats.R
import com.app.mychats.model.Contact
import com.app.mychats.model.UsersAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_chats.view.*

class OneToOneChatsFragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var contactList: MutableList<Contact>

    private lateinit var chatRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chats, container, false)
        contactList = mutableListOf()
        val usersAdapter = UsersAdapter(requireContext(), contactList)
        chatRecyclerView = view.chatRecyclerView
        chatRecyclerView.adapter = usersAdapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        chatRecyclerView.layoutManager = linearLayoutManager

        database = FirebaseDatabase.getInstance()
        database.reference.child("contacts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                dataSnapshot.children.forEach { data ->
                    Log.d("existence", data.exists().toString())
                    val contact = data.getValue(Contact::class.java)
                    contactList.add(contact!!)
                }
                usersAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message

            }
        })
        return view
    }

}