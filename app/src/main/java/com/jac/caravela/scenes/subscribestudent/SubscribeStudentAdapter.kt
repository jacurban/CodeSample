package com.jac.caravela.scenes.subscribestudent


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jac.caravela.R
import com.jac.caravela.model.User
import kotlinx.android.synthetic.main.item_list.view.*

class SubscribeStudentAdapter(private val students: MutableList<User>, private val listener: Listener) :
    androidx.recyclerview.widget.RecyclerView.Adapter<SubscribeStudentAdapter.ViewHolder>() {

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
        holder.itemView.item_list_CTL?.setOnClickListener {
            listener.showAlertDialogToConfirmEnrollment(students[position])
        }
    }

    class ViewHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item)

    interface Listener {
        fun showAlertDialogToConfirmEnrollment(student: User)
    }
}