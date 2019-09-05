package com.jac.caravela.scenes.matriculalist

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jac.caravela.R
import com.jac.caravela.model.Enrollment
import com.jac.caravela.model.EnrollmentStatus
import com.jac.caravela.scenes.matriculainfo.MatriculaInfoActivity
import kotlinx.android.synthetic.main.item_matricula.view.*

class MatriculaListAdapter(private val enrollment: MutableList<Enrollment>, private val activity: Activity?) :
    RecyclerView.Adapter<MatriculaListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matricula, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return enrollment.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_matricula_student_TXV?.text = enrollment[position].student.name
        holder.itemView.item_matricula_course_TXV?.text = enrollment[position].courseName
        holder.itemView.item_matricula_class_TXV?.text = enrollment[position].className

        holder.itemView.item_matricula_CTL?.setOnClickListener {
            val intent = Intent(activity, MatriculaInfoActivity::class.java)
            intent.putExtra(MatriculaInfoActivity.EXTRA_STUDENT_INFO, enrollment[position])
            activity?.startActivity(intent)
        }

        when (enrollment[position].statusId) {
            EnrollmentStatus.PENDING.value -> holder.itemView.item_matricula_CTL?.setBackgroundResource(R.drawable.round_item_matricula_pendente)
            EnrollmentStatus.APPROVED.value -> holder.itemView.item_matricula_CTL?.setBackgroundResource(R.drawable.round_item_matricula_aprovada)
            EnrollmentStatus.REFUSED.value -> holder.itemView.item_matricula_CTL?.setBackgroundResource(R.drawable.round_item_matricula_rejeitada)
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)
}