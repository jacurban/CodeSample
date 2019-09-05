package com.jac.caravela.scenes.calendarioteacher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Classe
import com.jac.caravela.model.Lesson
import kotlinx.android.synthetic.main.item_calendario.view.*
import kotlinx.android.synthetic.main.item_evento_mapa.view.*

class CalendarioAdapter(private val listener: Listener) :
    RecyclerView.Adapter<CalendarioAdapter.ViewHolder>() {

    var classes: List<Classe> = emptyList()
    var lessons: List<Lesson> = emptyList()
        set(value) {
            val calendar = mutableListOf<Lesson>()
            for (i in 1..7) {
                val dayLessons = value.filter { it.day == i }.sortedBy { it.hour }.sortedBy { it.minute }
                dayLessons.firstOrNull()?.showDayOfWeek = true
                calendar.addAll(dayLessons)
            }
            field = calendar
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendario, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lessons[position], classes[lessons[position].classId ?: 0])
    }

    class ViewHolder(item: View, private val listener: Listener) : RecyclerView.ViewHolder(item) {
        fun bind(lesson: Lesson, classe: Classe?) {
            val time = lesson.hour + ":" + lesson.minute

            itemView.item_calendario_time_TXV?.text = time
            itemView.item_calendario_course_name_TXV?.text = classe?.courseName
            itemView.item_calendario_class_name_TXV?.text = classe?.name
            itemView.item_calendario_class_reference_TXV?.text = classe?.localName

            if (lesson.showDayOfWeek) {
                itemView.item_calendario_day_TXV?.text = lesson.getDayAsString()
                itemView.item_calendario_day_TXV?.visibility = View.VISIBLE
            } else {
                itemView.item_calendario_day_TXV?.visibility = View.GONE
            }

            if (App.user?.isTeacher() == true || App.user?.isResponsible() == true) {
                itemView.item_calendario_info_V?.visibility = View.VISIBLE
                itemView.item_calendario_CL?.setOnClickListener {
                    classe?.let { listener.goToTurmaInfoActivity(it) }
                }
            }
        }
    }

    interface Listener {
        fun goToTurmaInfoActivity(classInfo: Classe)
    }
}