package com.jac.caravela.scenes.partnercadastro

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jac.caravela.R
import com.jac.caravela.extensions.checkIfIsBlank
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Partner
import kotlinx.android.synthetic.main.activity_partner_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*


class PartnerCadastroActivity : AppCompatActivity(), PartnerCadastro.View {
    private lateinit var presenter: PartnerCadastro.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner_cadastro)

        presenter = PartnerCadastroPresenter(this)

        setupToolbar()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_title?.text = getString(R.string.novo_parceiro)
    }

    private fun setupViewElements() {
        val partner: Partner? = intent.getParcelableExtra(EXTRA_PARTNER_INFO)
        if (partner != null) {
            parceiro_cadastro_save_BTN?.text = getString(R.string.editar)
            parceiro_cadastro_name_EDT?.setText(partner.name)
            parceiro_cadastro_email_EDT?.setText(partner.email)
            parceiro_cadastro_cnpj_EDT?.setText(partner.cnpj)
            parceiro_cadastro_description_EDT?.setText(partner.description)
            toolbar_title?.visibility = View.GONE
        } else {
            parceiro_cadastro_save_BTN?.text = getString(R.string.finish_registration)
        }

        parceiro_cadastro_save_BTN?.setOnClickListener {
            if (validFields()) {
                val partnerName = parceiro_cadastro_name_EDT.text.toString()
                val partnerEmail = parceiro_cadastro_email_EDT.text.toString()
                val partnerCnpj = parceiro_cadastro_cnpj_EDT.text.toString()
                val partnerDescription = parceiro_cadastro_description_EDT.text.toString()

                toolbar_progressbar?.visibility = View.VISIBLE
                if (partner != null)
                    presenter.postEditPartner(partner.id, partnerName, partnerCnpj, partnerEmail, partnerDescription)
                else
                    presenter.postRegisterPartner(partnerName, partnerCnpj, partnerEmail, partnerDescription)
            }
        }
    }

    private fun validFields(): Boolean {
        if (parceiro_cadastro_name_EDT.checkIfIsBlank()
            || parceiro_cadastro_email_EDT.checkIfIsBlank()
            || parceiro_cadastro_cnpj_EDT.checkIfIsBlank())
            return false

        return true
    }

    override fun successfulRegister() {
        finish()
    }

    override fun unsuccessfulRegister(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        toolbar_progressbar?.visibility = View.GONE
        unsuccessfulCall(msgRef)
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val REQCODE_FAB = 1
        const val REQCODE_EDIT = 2
        const val EXTRA_PARTNER_INFO = "EXTRA_PARTNER_INFO"
    }
}