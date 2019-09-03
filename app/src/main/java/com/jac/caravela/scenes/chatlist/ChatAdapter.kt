package com.jac.caravela.scenes.chatlist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Chat
import com.jac.caravela.scenes.chatpage.ChatPageActivity
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(private val chats: MutableList<Chat>, private val listener: Listener) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (chats[position].userOneId == App.user?.id) {
            holder.itemView.item_message_name_TXV?.text = chats[position].userTwoName ?: listener.getString(R.string.deleted_user)
            holder.itemView.item_message_type_TXT?.text =
                if (chats[position].getUserTwoTypeRef() != 0)
                    listener.getString(chats[position].getUserTwoTypeRef())
                else
                    ""
            if (listener.getChatType() == ChatType.CHAT) {
                holder.itemView.item_message_new_message?.visibility = View.GONE
                if (chats[position].userOneStatusId == 2)
                    holder.itemView.item_message_warning?.visibility = View.GONE
                else {
                    holder.itemView.item_message_warning?.visibility = View.VISIBLE
                }
            }
        } else {
            holder.itemView.item_message_name_TXV?.text = chats[position].userOneName ?: listener.getString(R.string.deleted_user)
            holder.itemView.item_message_type_TXT?.text =
                if (chats[position].getUserOneTypeRef() != 0)
                    listener.getString(chats[position].getUserOneTypeRef())
                else
                    ""
            if (listener.getChatType() == ChatType.CHAT) {
                holder.itemView.item_message_new_message?.visibility = View.GONE
                if (chats[position].userTwoStatusId == 2)
                    holder.itemView.item_message_warning?.visibility = View.GONE
                else {
                    holder.itemView.item_message_warning?.visibility = View.VISIBLE
                }
            }
        }

        if (listener.getChatType() == ChatType.NEW_CHAT) {
            holder.itemView.item_message_warning?.visibility = View.GONE
            holder.itemView.item_message_new_message?.visibility = View.VISIBLE
        }

        holder.itemView.item_message_CTL?.setOnClickListener {
            val intent = Intent(listener.getContext(), ChatPageActivity::class.java)
            intent.putExtra(ChatPageActivity.EXTRA_CHAT, chats[position])
            listener.startActivity(intent)
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    interface Listener {
        fun getChatType(): ChatType
        fun getString(reference: Int): String?
        fun getContext(): Context?
        fun startActivity(intent: Intent)
    }

    enum class ChatType {
        CHAT,
        NEW_CHAT
    }
}
