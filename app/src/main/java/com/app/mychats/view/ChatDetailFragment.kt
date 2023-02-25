package com.app.mychats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mychats.R
import com.app.mychats.model.ChatAdapter
import com.app.mychats.model.Message
import com.app.mychats.util.MyChatUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_chat_detail.view.*
import java.util.Date

class ChatDetailFragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var receiverId: String
    private lateinit var receiverName: String
    private lateinit var profilePic: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat_detail, container, false)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val senderId = auth.uid

        arguments?.run {
            receiverId = getString("contactId").toString()
            profilePic = getString("profilePic").toString()
            receiverName = getString("receiverName").toString()
        }

        view.txtName.text = receiverName
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_person).into(view.imgViewUser)

        view.imgViewBack.setOnClickListener {
            MyChatUtil.replaceFragment(activity, HomeFragment())
        }

        val messageList = mutableListOf<Message>()

        val chatAdapter = ChatAdapter(requireContext(), messageList)
        view.chatRecyclerView.adapter = chatAdapter

        val layoutManager = LinearLayoutManager(requireContext())
        view.chatRecyclerView.layoutManager = layoutManager

        val senderRoom = senderId + receiverId
        val receiverRoom = receiverId + senderId

        database.reference.child("chats")
            .child(senderRoom)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    snapshot.children.forEach {
                        val message = it.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    chatAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        view.imgViewSend.setOnClickListener {
            val messageText = view.etMessage.text.toString()
            val timestamp = Date().time.toString()
            val message = Message(senderId!!, messageText, timestamp)
            view.etMessage.setText("")

            database.reference.child("chats").child(senderRoom).push()
                .setValue(message).addOnSuccessListener {
                    database.reference.child("chats")
                        .child(receiverRoom).push().setValue(message)
                        .addOnSuccessListener {

                        }
                }
        }
        return view
    }

}