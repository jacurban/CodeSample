package com.jac.caravela.scenes.cursocadastro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.jac.caravela.model.Course
import com.jac.caravela.R
import com.jac.caravela.extensions.checkIfIsBlank
import com.jac.caravela.extensions.showShortMessage
import kotlinx.android.synthetic.main.activity_curso_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*

class CursoCadastroActivity : AppCompatActivity(), CursoCadastro.View {
    private lateinit var presenter: CursoCadastro.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curso_cadastro)

        presenter = CursoCadastroPresenter(this)

        setupToolbar()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_title?.text = getString(R.string.novo_curso)
    }

    private fun setupViewElements() {
        val course: Course? = intent.getParcelableExtra(EXTRA_EDIT)
        if (course != null) {
            curso_cadastro_save_BTN?.text = getString(R.string.editar)
            curso_cadastro_class_EDT?.setText(course.name)
            curso_cadastro_description_EDT?.setText(course.description)
            toolbar_title?.visibility = View.GONE
        } else {
            curso_cadastro_save_BTN?.text = getString(R.string.finish_registration)
        }

        curso_cadastro_save_BTN?.setOnClickListener {
            if (validFields()) {
                val courseName = curso_cadastro_class_EDT.text.toString()
                val courseDescription = curso_cadastro_description_EDT.text.toString()

                toolbar_progressbar?.visibility = View.VISIBLE
                if (course!= null)
                    presenter.postEditCourse(course.id, courseName, courseDescription)
                else
                    presenter.postRegiterCourse(courseName, courseDescription)
            }
        }
    }

    private fun validFields(): Boolean {
        if (curso_cadastro_class_EDT.checkIfIsBlank() || curso_cadastro_description_EDT.checkIfIsBlank())
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
        const val EXTRA_EDIT = "EXTRA_EDIT"
    }
}