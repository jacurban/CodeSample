package com.jac.caravela.scenes.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jac.caravela.BuildConfig
import com.jac.caravela.R
import com.jac.caravela.extensions.checkIfIsBlank
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.scenes.main.MainActivity
import com.jac.caravela.scenes.parentcadastro.ParentCadastroActivity
import com.jac.caravela.tools.Validators
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.view.*

class LoginActivity : AppCompatActivity(), Login.View {

    private lateinit var presenter: Login.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)

        setupViewElements()
        setupMocked()
    }

    override fun postLoginSuccessful() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun postLoginUnsuccessful(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        login_enter_BTN?.apply {
            isClickable = true
            isActivated = true
            toolbar_progressbar?.visibility = View.INVISIBLE
        }
    }

    private fun setupViewElements() {
        login_enter_BTN?.apply {
            isActivated = true
            setOnClickListener {

                if (validFields()) {
                    val email = login_EDT?.text.toString()
                    val password = password_EDT?.text.toString()
                    presenter.postLogin(email, password)

                    isClickable = false
                    isActivated = false
                    toolbar_progressbar?.visibility = View.VISIBLE
                }
            }
        }

        login_new_user_BTN?.setOnClickListener {
            val intent = Intent(this, ParentCadastroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupMocked() {
        if (BuildConfig.DEBUG) {
            login_EDT?.setText("adm@caravela.com.br")
            password_EDT?.setText("123456")
        }
    }

    private fun validFields(): Boolean {

        if (login_EDT.checkIfIsBlank() || password_EDT.checkIfIsBlank())
            return false

        if (!Validators.isValidEmail(login_EDT?.text.toString())) {
            login_EDT?.error = getString(R.string.error_invalid_email)
            return false
        }

        return true
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }
}