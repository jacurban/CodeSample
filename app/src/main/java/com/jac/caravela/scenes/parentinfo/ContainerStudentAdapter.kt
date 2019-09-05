package com.jac.caravela.scenes.parentinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jac.caravela.R
import com.jac.caravela.model.User
import kotlinx.android.synthetic.main.item_container_student.view.*

class ContainerStudentAdapter(private val students: MutableList<User>) :
    RecyclerView.Adapter<ContainerStudentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_container_student_name_TXV?.text = students[position].name

    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

}