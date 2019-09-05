package com.jac.caravela.scenes.partner

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jac.caravela.R
import com.jac.caravela.model.Partner
import com.jac.caravela.scenes.partnercadastro.PartnerCadastroActivity
import kotlinx.android.synthetic.main.item_parceiros.view.*

class PartnerAdapter(private val partners: MutableList<Partner>, private val listener: Listener) :
    androidx.recyclerview.widget.RecyclerView.Adapter<PartnerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parceiros, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return partners.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_parceiros_name_TXV?.text = partners[position].name
        holder.itemView.item_parceiros_email_TXV?.text = partners[position].email
        holder.itemView.item_parceiros_cnpj_TXV?.text = partners[position].cnpj
        holder.itemView.item_parceiros_description_TXV?.text = partners[position].description

        if (listener.showAdmOptions()) {
            holder.itemView.item_parceiros_delete_BTN?.visibility = View.VISIBLE
            holder.itemView.item_parceiros_delete_BTN?.setOnClickListener {
                listener.deletePartner(partners[position].name, partners[position].id)
            }

            holder.itemView.item_parceiros_edit_BTN?.visibility = View.VISIBLE
            holder.itemView.item_parceiros_edit_BTN?.setOnClickListener {
                val intent = Intent(listener.getContext(), PartnerCadastroActivity::class.java)
                intent.putExtra(PartnerCadastroActivity.EXTRA_PARTNER_INFO, partners[position])
                listener.openActivity(intent)
            }
        } else {
            holder.itemView.item_parceiros_delete_BTN?.visibility = View.GONE
            holder.itemView.item_parceiros_edit_BTN?.visibility = View.GONE
        }
    }

    class ViewHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item)

    interface Listener {
        fun deletePartner(partnerName: String, partnerId: Int)
        fun getContext(): Context?
        fun openActivity(intent: Intent)
        fun showAdmOptions(): Boolean
    }
}