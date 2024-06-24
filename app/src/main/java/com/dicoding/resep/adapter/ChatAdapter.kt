package com.bangkit.resep.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.resep.R
import com.bangkit.resep.models.Message

class ChatAdapter(private val messages: List<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_USER_MESSAGE = 1
    private val VIEW_TYPE_BOT_MESSAGE = 2

    inner class UserMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.textViewUserMessage)

        fun bind(message: Message) {
            messageTextView.text = message.content
        }
    }

    inner class BotMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.textViewBotMessage)

        fun bind(message: Message) {
            messageTextView.text = message.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_USER_MESSAGE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_message, parent, false)
            UserMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bot_message, parent, false)
            BotMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (getItemViewType(position) == VIEW_TYPE_USER_MESSAGE) {
            (holder as UserMessageViewHolder).bind(message)
        } else {
            (holder as BotMessageViewHolder).bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isSent) {
            VIEW_TYPE_USER_MESSAGE
        } else {
            VIEW_TYPE_BOT_MESSAGE
        }
    }
}