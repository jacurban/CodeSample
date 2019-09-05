package com.jac.caravela.scenes.coordcadastro

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jac.caravela.R
import com.jac.caravela.extensions.checkIfIsBlank
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.User
import com.jac.caravela.tools.PhoneNumberFormatType
import com.jac.caravela.tools.PhoneNumberFormatter
import com.jac.caravela.tools.Validators
import kotlinx.android.synthetic.main.activity_user_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

class CoordCadastroActivity : AppCompatActivity(), CoordCadastro.View {

    private lateinit var presenter: CoordCadastro.Presenter
    private var date: String? = null
    private var selectedDate: String? = null
    private var coordInfo: User? = null
    private var admInfo: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cadastro)

        presenter = CoordCadastroPresenter(this)
        coordInfo = intent.getParcelableExtra(EXTRA_COORD_EDIT)
        admInfo = intent.getParcelableExtra(EXTRA_ADM_EDIT)

        setupToolbar()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_title?.text = getString(R.string.novo_coordenador)
    }

    private fun setupViewElements() {
        val phoneFormatter = PhoneNumberFormatter(WeakReference(user_cadastro_phone_EDT), PhoneNumberFormatType.PT_BR)
        user_cadastro_phone_EDT?.addTextChangedListener(phoneFormatter)

        when {
            coordInfo != null -> {
                user_cadastro_register_BTN?.text = getString(R.string.editar)
                toolbar_title?.text = getString(R.string.editar)
                user_cadastro_name_EDT?.setText(coordInfo?.name)
                user_cadastro_cpf_EDT?.setText(coordInfo?.cpf)
                user_cadastro_phone_EDT?.setText(coordInfo?.telephone)
                user_cadastro_email_EDT?.setText(coordInfo?.email)
                user_cadastro_password_EDT?.setText(coordInfo?.password)
            }
            admInfo != null -> {
                user_cadastro_register_BTN?.text = getString(R.string.editar)
                toolbar_title?.text = getString(R.string.editar)
                user_cadastro_name_EDT?.setText(admInfo?.name)
                user_cadastro_cpf_EDT?.setText(admInfo?.cpf)
                user_cadastro_phone_EDT?.setText(admInfo?.telephone)
                user_cadastro_email_EDT?.setText(admInfo?.email)
                user_cadastro_password_EDT?.setText(admInfo?.password)
            }
            else -> user_cadastro_register_BTN?.text = getString(R.string.finish_registration)
        }

        user_cadastro_birth_TXT?.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedDate = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
                date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(cal.time)
                user_cadastro_birth_TXT?.text = selectedDate
            }
            DatePickerDialog(
                this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, dateSetListener, cal.get(
                    Calendar.YEAR
                ), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        user_cadastro_register_BTN?.setOnClickListener {
            if (validFields()) {
                val userName = user_cadastro_name_EDT?.text.toString()
                val userBirth = date ?: return@setOnClickListener
                val userCpf = user_cadastro_cpf_EDT?.text.toString()
                val userPhone = user_cadastro_phone_EDT?.text.toString()
                val userEmail = user_cadastro_email_EDT?.text.toString()
                val userPassword = user_cadastro_password_EDT?.text.toString()

                if (coordInfo == null && admInfo == null) {
                    presenter.postRegisterCoordinator(
                        userName, userBirth, userCpf, userPhone, userEmail, userPassword
                    )
                } else if (coordInfo != null) {
                    presenter.postEditCoordinator(
                        coordInfo?.id, userName, userBirth, userCpf, userPhone, userEmail
                    )
                } else if (admInfo != null) {
                    presenter.postEditAdministrator(
                        admInfo?.id, userName, userBirth, userCpf, userPhone, userEmail
                    )
                }
            }
        }
    }

    private fun validFields(): Boolean {
        if (user_cadastro_name_EDT.checkIfIsBlank()
            || user_cadastro_cpf_EDT.checkIfIsBlank()
            || user_cadastro_phone_EDT.checkIfIsBlank()
            || user_cadastro_email_EDT.checkIfIsBlank()
            || user_cadastro_password_EDT.checkIfIsBlank()
        )
            return false

        if (!Validators.isValidEmail(user_cadastro_email_EDT?.text.toString())) {
            user_cadastro_email_EDT?.error = getString(R.string.error_invalid_email)
            return false
        }

        if (date == null) {
            user_cadastro_birth_TXT?.error = getString(R.string.error_invalid_email)
            return false
        }

        user_cadastro_password_EDT?.let {
            if (user_cadastro_password_EDT.length() < 6 || user_cadastro_password_EDT.length() > 12) {
                user_cadastro_password_EDT.error = getString(R.string.error_invalid_password)
                return false
            }
        }
        return true
    }

    override fun successfulEdit() {
        val i = Intent()
        setResult(Activity.RESULT_OK, i)
        finish()
    }

    override fun successfulRegister() {
        toolbar_progressbar?.visibility = View.GONE
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
        const val EXTRA_COORD_EDIT = "EXTRA_COORD_EDIT"
        const val EXTRA_ADM_EDIT = "EXTRA_ADM_EDIT"
        const val REQCODE_EDIT = 1
    }
}