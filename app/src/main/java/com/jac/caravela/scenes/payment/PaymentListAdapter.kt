package com.jac.caravela.scenes.payment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jac.caravela.R
import com.jac.caravela.model.Payment
import com.jac.caravela.model.PaymentStatus
import kotlinx.android.synthetic.main.item_payment.view.*
import java.text.SimpleDateFormat


class PaymentListAdapter(var payments: MutableList<Payment>, private val listener: Listener) :
    RecyclerView.Adapter<PaymentListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val month = SimpleDateFormat("MMM").format(payments[position].maturityDate)
        val dueDate = SimpleDateFormat("dd/MM/yyyy").format(payments[position].maturityDate)

        holder.itemView.item_payment_month_TXV?.text = month
        holder.itemView.item_payment_due_TXV?.text = dueDate
        holder.itemView.item_payment_course_TXV?.text = payments[position].courseName
        holder.itemView.item_payment_class_TXV?.text = payments[position].studentName
        holder.itemView.item_payment_value_TXV?.text = payments[position].price.toString()

        when (payments[position].statusId){
            PaymentStatus.WAITINGPAYMENT.value -> holder.itemView.item_payment_status_TXV?.text = "Em aberto"
            PaymentStatus.PROCESSING.value -> holder.itemView.item_payment_status_TXV?.text = "Processando"
            PaymentStatus.PAID.value -> holder.itemView.item_payment_status_TXV?.text = "Pago"
            PaymentStatus.REFUSED.value -> holder.itemView.item_payment_status_TXV?.text = "Recusado"
            else ->  holder.itemView.item_payment_status_TXV?.text = "Em aberto"
        }

        holder.itemView.item_payment_CTL?.setOnClickListener {
            listener.goToPaymentPage(payments[position].boletoUrl)
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {}
    interface Listener {
        fun goToPaymentPage(link: String)
    }
}