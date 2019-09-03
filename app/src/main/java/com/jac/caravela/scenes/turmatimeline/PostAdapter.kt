package com.jac.caravela.scenes.turmatimeline

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Post
import kotlinx.android.synthetic.main.item_message.view.*
import java.text.SimpleDateFormat

class PostAdapter(private val postMessages: MutableList<Post>, private val activity: Activity?) :
    androidx.recyclerview.widget.RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postMessages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val daytime = simpleDateFormat.format(chatMessages[position].sendDate)

        holder.itemView.item_chat_subject_TXV?.text = postMessages[position].description
        holder.itemView.item_chat_time_TXV?.text = postMessages[position].createDate

        if (postMessages[position].userId == App.user?.id) {
            holder.itemView.item_chat_name_TXV?.text = activity?.getString(R.string.voce)
            holder.itemView.item_chat_CTL?.setBackgroundResource(R.drawable.round_bckg_light_red)
        }else{
            holder.itemView.item_chat_name_TXV?.text = postMessages[position].userName
            holder.itemView.item_chat_CTL?.setBackgroundResource(R.drawable.round_bckg_white)
        }
    }

    class ViewHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item)
}