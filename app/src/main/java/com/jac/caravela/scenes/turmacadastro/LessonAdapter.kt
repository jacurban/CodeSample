package com.jac.caravela.scenes.turmacadastro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Lesson
import kotlinx.android.synthetic.main.item_list.view.*

class LessonAdapter(private var lessons: MutableList<Lesson>, private val listener: Listener) :
    RecyclerView.Adapter<LessonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_short, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        lessons[position].let {
            val time = it.getDayAsString() + " - " + it.hour + ":" + it.minute
            holder.itemView.item_list_name_TXV?.text = time
        }

        if (listener.showDeleteLesson())
            holder.itemView.item_list_delete_BTN?.visibility = View.VISIBLE
        else
            holder.itemView.item_list_delete_BTN?.visibility = View.GONE

        holder.itemView.item_list_delete_BTN?.setOnClickListener {
            listener.deteleLesson(position)
        }

        if (App.user?.isResponsible() == true || App.user?.isTeacher() == true)
            holder.itemView.item_list_delete_BTN?.visibility = View.GONE

    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    interface Listener {
        fun deteleLesson(position: Int)
        fun showDeleteLesson(): Boolean
    }
}