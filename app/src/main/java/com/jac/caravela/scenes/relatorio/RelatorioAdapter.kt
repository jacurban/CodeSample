package com.jac.caravela.scenes.relatorio

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jac.caravela.R
import com.jac.caravela.model.Report
import kotlinx.android.synthetic.main.item_relatorio.view.*
import java.text.SimpleDateFormat

class RelatorioAdapter(private var reports: MutableList<Report>) :
    RecyclerView.Adapter<RelatorioAdapter.ViewHolder>() {

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("MM-yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_relatorio, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reports.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = simpleDateFormat.format(reports[position].date)
        holder.itemView.item_relatorio_date_TXV?.text = date
        holder.itemView.item_relatorio_content_TXV?.text = reports[position].count.toString()
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)
}