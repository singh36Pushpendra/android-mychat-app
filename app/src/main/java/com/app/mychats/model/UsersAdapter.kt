package com.app.mychats.model

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.mychats.R
import com.app.mychats.util.MyChatUtil
import com.app.mychats.view.ChatDetailFragment
import com.app.mychats.view.HomeFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.chats_card_view.view.*

class UsersAdapter(val context: Context, val contactList: MutableList<Contact>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgViewUser = itemView.imgViewUser
        val contactName = itemView.contactName
        val lastMessage = itemView.lastMessage

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chats_card_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact: Contact = contactList[position]
        holder.itemView.setOnClickListener {
            val chatDetailFragment = ChatDetailFragment()
            val bundle = Bundle()
            bundle.putString("contactId", contact.contactId)
            bundle.putString("profilePic", contact.profilePic)
            bundle.putString("contactName", contact.name)
            chatDetailFragment.arguments = bundle
            MyChatUtil.replaceFragment(context as AppCompatActivity, chatDetailFragment)
        }
        Picasso.get().load(contact.profilePic).placeholder(R.drawable.ic_person)
            .into(holder.imgViewUser)
        holder.contactName.text = contact.name
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}