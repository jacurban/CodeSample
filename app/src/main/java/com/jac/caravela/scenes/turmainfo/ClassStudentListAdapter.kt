package com.jac.caravela.scenes.turmainfo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.User
import com.jac.caravela.scenes.studentinfo.StudentInfoActivity
import kotlinx.android.synthetic.main.item_list.view.*

class ClassStudentListAdapter(private val students: MutableList<User>, private val listener: Listener) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ClassStudentListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_list_name_TXV?.text = students[position].name

        holder.itemView.item_list_delete_BTN?.visibility = View.GONE

        if (App.user?.isTeacher() == true || App.user?.isAdministrator() == true || App.user?.isCoordinator() == true) {
            holder.itemView.item_list_CTL?.setOnClickListener {
                val intent = Intent(listener.getContext(), StudentInfoActivity::class.java)
                intent.putExtra(StudentInfoActivity.EXTRA_STUDENT_INFO, students[position])
                listener.startActivity(intent)
            }
        }
    }

    class ViewHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item)

    interface Listener {
        fun startActivity(intent: Intent)
        fun getContext(): Context?
    }
}