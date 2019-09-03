package com.jac.caravela.scenes.usercadastro

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.User
import com.jac.caravela.scenes.coordinfo.CoordInfoActivity
import com.jac.caravela.scenes.parentinfo.ParentInfoActivity
import com.jac.caravela.scenes.studentinfo.StudentInfoActivity
import com.jac.caravela.scenes.teacherinfo.TeacherInfoActivity
import kotlinx.android.synthetic.main.item_list.view.*

class UserAdapter(private val users: MutableList<User>, private val listener: Listener) :
    androidx.recyclerview.widget.RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_list_name_TXV?.text = users[position].name

        holder.itemView.item_list_delete_BTN?.setOnClickListener {
            listener.deleteUser(users[position].name, users[position].id)
        }

        if ((App.user?.isAdministrator() == true || App.user?.isCoordinator() == true)
            && (users[position].isStudent() || users[position].isResponsible())) {
            holder.itemView.item_list_delete_BTN?.visibility = View.GONE
        }

        when {
            users[position].isStudent() -> holder.itemView.item_list_CTL?.setOnClickListener {
                val intent = Intent(listener.getContext(), StudentInfoActivity::class.java)
                intent.putExtra(StudentInfoActivity.EXTRA_STUDENT_INFO, users[position])
                listener.startActivity(intent)
            }
            users[position].isResponsible() -> holder.itemView.item_list_CTL?.setOnClickListener {
                val intent = Intent(listener.getContext(), ParentInfoActivity::class.java)
                intent.putExtra(ParentInfoActivity.EXTRA_PARENT_INFO, users[position])
                listener.startActivity(intent)
            }
            users[position].isTeacher() -> holder.itemView.item_list_CTL?.setOnClickListener {
                val intent = Intent(listener.getContext(), TeacherInfoActivity::class.java)
                intent.putExtra(TeacherInfoActivity.EXTRA_TEACHER_INFO, users[position])
                listener.startActivity(intent)
            }
            users[position].isCoordinator() -> holder.itemView.item_list_CTL?.setOnClickListener {
                val intent = Intent(listener.getContext(), CoordInfoActivity::class.java)
                intent.putExtra(CoordInfoActivity.EXTRA_COORD_INFO, users[position])
                listener.startActivity(intent)
            }
        }
    }

    class ViewHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item)

    interface Listener {
        fun deleteUser(userName: String, userId: Int)
        fun startActivity(intent: Intent)
        fun getContext(): Context?
    }
}