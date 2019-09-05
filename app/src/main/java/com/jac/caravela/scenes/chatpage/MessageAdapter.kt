package com.jac.caravela.scenes.chatpage

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Message
import kotlinx.android.synthetic.main.item_message.view.*
import java.text.SimpleDateFormat

class MessageAdapter(private val chatMessages: MutableList<Message>, private val activity: Activity?) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val sendDate = SimpleDateFormat("dd/MM").format(chatMessages[position].sendDate)
        val sendTime = SimpleDateFormat("HH:mm").format(chatMessages[position].sendDate)
        val datetime = "$sendTime - $sendDate"

        holder.itemView.item_chat_subject_TXV?.text = chatMessages[position].description
        holder.itemView.item_chat_time_TXV?.text = datetime

        if (chatMessages[position].senderId == App.user?.id) {
            holder.itemView.item_chat_name_TXV?.text = activity?.getString(R.string.voce)
            holder.itemView.item_chat_CTL?.setBackgroundResource(R.drawable.round_bckg_light_red)
        }else{
            holder.itemView.item_chat_name_TXV?.text = chatMessages[position].senderName
            holder.itemView.item_chat_CTL?.setBackgroundResource(R.drawable.round_bckg_white)
        }
    }

    class ViewHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item)
}