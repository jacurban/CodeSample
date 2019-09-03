package com.jac.caravela.scenes.matriculainfo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jac.caravela.R
import com.jac.caravela.model.Payment
import com.jac.caravela.model.PaymentStatus
import kotlinx.android.synthetic.main.item_matricula_payment.view.*
import java.text.SimpleDateFormat

class MatriculaInfoAdapter (private val payments: List<Payment>) :
    RecyclerView.Adapter<MatriculaInfoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matricula_payment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val month = SimpleDateFormat("MMM").format(payments[position].maturityDate)
        holder.itemView.matricula_payment_month?.text = month

        when (payments[position].statusId){
            PaymentStatus.WAITINGPAYMENT.value -> holder.itemView.matricula_payment_status?.text = "Em aberto"
            PaymentStatus.PROCESSING.value -> holder.itemView.matricula_payment_status?.text = "Processando"
            PaymentStatus.PAID.value -> holder.itemView.matricula_payment_status?.text = "Pago"
            PaymentStatus.REFUSED.value -> holder.itemView.matricula_payment_status?.text = "Recusado"
            else ->  holder.itemView.matricula_payment_status?.text = "Em aberto"
        }

    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)
}