package com.app.mychats.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mychats.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sample_receiver.view.*
import kotlinx.android.synthetic.main.sample_sender.view.*

class ChatAdapter(val context: Context, val messageList: MutableList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiverMessage = itemView.receiverMessage
        val receiverTime = itemView.receiverTime

    }

    class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderMessage = itemView.senderMessage
        val senderTime = itemView.senderTime
    }

    override fun getItemViewType(position: Int): Int =
        if (messageList[position].userId == FirebaseAuth.getInstance().currentUser!!.uid)
            SENDER_VIEW_TYPE
        else
            RECEIVER_VIEW_TYPE

    companion object {
        private const val SENDER_VIEW_TYPE = 1
        private const val RECEIVER_VIEW_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == SENDER_VIEW_TYPE) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false)
            return SenderViewHolder(view)
        }
        else {
            val view =
                LayoutInflater.from(context).inflate(R.layout.sample_receiver, parent, false)
            return ReceiverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]

        if (holder.javaClass == SenderViewHolder::class.java) {
            (holder as SenderViewHolder).senderMessage.text = message.message
        }
        else {
            (holder as ReceiverViewHolder).receiverMessage.text = message.message
        }
    }

    override fun getItemCount(): Int = messageList.size
}