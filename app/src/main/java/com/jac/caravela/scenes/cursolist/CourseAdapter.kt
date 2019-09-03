package com.jac.caravela.scenes.cursolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Course
import kotlinx.android.synthetic.main.item_list.view.*

class CourseAdapter(private val courses: MutableList<Course>, private val listener: Listener) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_list_name_TXV.text = courses[position].name

        if (App.user?.isResponsible() == true || App.user?.isTeacher() == true)
            holder.itemView.item_list_delete_BTN?.visibility = View.GONE

        holder.itemView.item_list_CTL?.setOnClickListener {
            listener.goToTurmaListActivity(courses[position])
        }

        holder.itemView.item_list_delete_BTN?.setOnClickListener {
            listener.deleteCourse(courses[position].name, courses[position].id)
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    interface Listener {
        fun goToTurmaListActivity(course: Course)
        fun deleteCourse(courseName: String, courseId : Int)
    }
}