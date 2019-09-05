package com.jac.caravela.scenes.turmalist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Classe
import com.jac.caravela.scenes.turmainfo.TurmaInfoActivity
import kotlinx.android.synthetic.main.item_list.view.*

class TurmaAdapter(private var classes: MutableList<Classe>, private val listener: Listener) :
    RecyclerView.Adapter<TurmaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return classes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_list_name_TXV?.text = classes[position].name

        holder.itemView.item_list_delete_BTN?.setOnClickListener {
            listener.deteleClass(classes[position].name ,classes[position].id)
        }

        if (App.user?.isResponsible() == true || App.user?.isTeacher() == true)
            holder.itemView.item_list_delete_BTN?.visibility = View.GONE

        holder.itemView.item_list_CTL?.setOnClickListener {
            listener.goToTurmaInfoActivity(classes[position], TurmaInfoActivity.SUBSCRIPTION_CODE)
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    interface Listener {
        fun goToTurmaInfoActivity(classInfo: Classe, code: Int)
        fun deteleClass(className:String, classId: Int)
    }
}