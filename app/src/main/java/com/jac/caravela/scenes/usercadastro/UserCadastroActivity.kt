package com.jac.caravela.scenes.usercadastro

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
import com.jac.caravela.model.UserType
import com.jac.caravela.tools.PhoneNumberFormatType
import com.jac.caravela.tools.PhoneNumberFormatter
import com.jac.caravela.tools.Validators
import kotlinx.android.synthetic.main.activity_user_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

class UserCadastroActivity : AppCompatActivity(), UserCadastro.View {

    private lateinit var presenter: UserCadastro.Presenter
    private var userType: Int? = null
    private var date: String? = null
    private var selectedDate: String? = null
    private var userInfo: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cadastro)

        presenter = UserCadastroPresenter(this)

        getArgs()
        setupToolbar()
        setupViewElements()
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

    private fun getArgs() {
        userInfo = intent.getParcelableExtra(EXTRA_USER_EDIT)

        val type = intent.getIntExtra(USER_TYPE, -1)
        if (type != -1)
            userType = type
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        when (userType) {
            UserType.STUDENT.value -> toolbar_title?.text = getString(R.string.novo_aluno)
            UserType.COORDINATOR.value -> toolbar_title?.text = getString(R.string.novo_coordenador)
            UserType.RESPONSIBLE.value -> toolbar_title?.text = getString(R.string.novo_responsavel)
            UserType.TEACHER.value -> toolbar_title?.text = getString(R.string.novo_professor)
            else -> toolbar_title?.text = getString(R.string.my_register)
        }
    }

    private fun setupViewElements() {
        val phoneFormatter = PhoneNumberFormatter(WeakReference(user_cadastro_phone_EDT), PhoneNumberFormatType.PT_BR)
        user_cadastro_phone_EDT?.addTextChangedListener(phoneFormatter)

        if (userInfo != null) {
            user_cadastro_register_BTN?.text = getString(R.string.editar)
            toolbar_title?.text = getString(R.string.editar)
            user_cadastro_name_EDT?.setText(userInfo?.name)
            user_cadastro_cpf_EDT?.setText(userInfo?.cpf)
            user_cadastro_phone_EDT?.setText(userInfo?.telephone)
            user_cadastro_email_EDT?.setText(userInfo?.email)
            user_cadastro_password_EDT?.setText(userInfo?.password)
        } else {
            user_cadastro_register_BTN?.text = getString(R.string.finish_registration)
        }

        user_cadastro_birth_TXT?.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedDate = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
                date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(cal.time)
                user_cadastro_birth_TXT?.setText(selectedDate)
            }
            DatePickerDialog(
                this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, dateSetListener, cal.get(
                    Calendar.YEAR
                ), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        if (userType == UserType.STUDENT.value || userInfo?.type == UserType.STUDENT.value) {
            label_password?.visibility = View.GONE
            user_cadastro_password_EDT?.visibility = View.GONE
        }

        user_cadastro_register_BTN?.setOnClickListener {
            if (validFields()) {
                val userName = user_cadastro_name_EDT?.text.toString()
                val userBirth = date ?: return@setOnClickListener
                val userCpf = user_cadastro_cpf_EDT?.text.toString()
                val userPhone = user_cadastro_phone_EDT?.text.toString()
                val userEmail = user_cadastro_email_EDT?.text.toString()
                val userPassword = user_cadastro_password_EDT?.text.toString()

                if (userType == UserType.STUDENT.value || userInfo?.type == UserType.STUDENT.value) {
                    if (userInfo != null)
                        presenter.postEditStudent(userInfo?.id, userName, userBirth, userCpf, userPhone, userEmail)
                    else {
                        presenter.postRegisterStudent(userName, userBirth, userCpf, userPhone, userEmail)
                    }
                } else {
                    if (userInfo != null) {
                        when (userType) {
                            UserType.RESPONSIBLE.value -> presenter.postEditResponsible(
                                userInfo?.id, userName, userBirth, userCpf, userPhone, userEmail, userPassword
                            )
                            UserType.TEACHER.value -> presenter.postEditTeacher(
                                userInfo?.id, userName, userBirth, userCpf, userPhone, userEmail
                            )
                            UserType.COORDINATOR.value -> presenter.postEditCoordinator(
                                userInfo?.id, userName, userBirth, userCpf, userPhone, userEmail
                            )
                            UserType.ADMINISTRATOR.value -> presenter.postEditAdministrator(
                                userInfo?.id, userName, userBirth, userCpf, userPhone, userEmail
                            )
                            else -> presenter.postRegisterResponsible(
                                userName, userBirth, userCpf, userPhone, userEmail, userPassword
                            )
                        }
                    } else {
                        when (userType) {
                            UserType.RESPONSIBLE.value -> presenter.postRegisterResponsible(
                                userName, userBirth, userCpf, userPhone, userEmail, userPassword
                            )
                            UserType.TEACHER.value -> presenter.postRegisterTeacher(
                                userName, userBirth, userCpf, userPhone, userEmail, userPassword
                            )
                            UserType.COORDINATOR.value -> presenter.postRegisterCoordinator(
                                userName, userBirth, userCpf, userPhone, userEmail, userPassword
                            )
                            UserType.ADMINISTRATOR.value -> presenter.postRegisterAdministrator(
                                userName, userBirth, userCpf, userPhone, userEmail, userPassword
                            )
                            else -> presenter.postRegisterResponsible(
                                userName, userBirth, userCpf, userPhone, userEmail, userPassword
                            )
                        }
                    }
                }
            }
        }
    }

    private fun validFields(): Boolean {
        if (user_cadastro_name_EDT.checkIfIsBlank()
            || user_cadastro_cpf_EDT.checkIfIsBlank()
            || user_cadastro_phone_EDT.checkIfIsBlank()
            || user_cadastro_email_EDT.checkIfIsBlank()
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
            if(user_cadastro_password_EDT.length() < 6 || user_cadastro_password_EDT.length() > 12) {
                user_cadastro_password_EDT.error = getString(R.string.error_invalid_password)
            }
            return false
        }

        return true
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val USER_TYPE = "USER_TYPE"
        const val REQCODE_NEW_PARENT = 1
        const val EXTRA_USER_EDIT = "EXTRA_USER_EDIT"
    }
}