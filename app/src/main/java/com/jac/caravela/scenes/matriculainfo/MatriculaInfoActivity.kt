package com.jac.caravela.scenes.matriculainfo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Enrollment
import kotlinx.android.synthetic.main.activity_matricula_info.*
import kotlinx.android.synthetic.main.toolbar.*

class MatriculaInfoActivity : AppCompatActivity(), MatriculaInfo.View {

    private lateinit var presenter: MatriculaInfo.Presenter
    private var enrollment: Enrollment? = null
    private var matriculaInfoAdapter : MatriculaInfoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matricula_info)
        setSupportActionBar(toolbar)

        presenter = MatriculaInfoPresenter(this)
        enrollment = intent.getParcelableExtra(EXTRA_STUDENT_INFO)

        enrollment?.payments?.let { matriculaInfoAdapter = MatriculaInfoAdapter(it) }
        matricula_payment_RCV?.adapter = matriculaInfoAdapter

        setupViewElements()
    }

    override fun onResume() {
        super.onResume()
        matriculaInfoAdapter?.notifyDataSetChanged()
    }

    private fun setupViewElements() {
        enrollment?.student?.asUser?.apply {
            matricula_student_name_TVT?.text = name
            matricula_student_CPF_TVT?.text = cpf
            matricula_student_email_TVT?.text = email
            matricula_student_phone_TVT?.text = telephone
            matricula_student_birth_TVT?.text = birthDateString
        }

        enrollment?.responsible?.asUser?.apply {
            matricula_parent_name_TXV?.text = name
            matricula_parent_cpf_TXV?.text = cpf
            matricula_parent_email_TXV?.text = email
            matricula_parent_phone_TXV?.text = telephone
            matricula_parent_birth_TVT?.text = birthDateString
        }

        matricula_class_TVT?.text = enrollment?.className
        matricula_course_TVT?.text = enrollment?.courseName

        toolbar_title?.text = enrollment?.student?.name

        if (enrollment?.statusId == 1) {
            matricula_reject_BTN?.visibility = View.VISIBLE
            matricula_aprove_BTN?.visibility = View.VISIBLE
            matricula_finish_BTN?.visibility = View.GONE
        } else {
            matricula_reject_BTN?.visibility = View.GONE
            matricula_aprove_BTN?.visibility = View.GONE
            if(enrollment?.statusId == 2) {
                matricula_finish_BTN?.visibility = View.VISIBLE
            } else{
                matricula_finish_BTN?.visibility = View.GONE
            }
        }

        matricula_reject_BTN?.setOnClickListener {
            showDialogToConfirmRejection(enrollment?.id)
        }

        matricula_aprove_BTN?.setOnClickListener {
            toolbar_progressbar?.visibility = View.VISIBLE
            showDialogToConfirmAccpeptance(enrollment?.id)
        }

        matricula_finish_BTN?.setOnClickListener {
            toolbar_progressbar?.visibility = View.VISIBLE
            showDialogToConfirmFinish(enrollment?.id)
        }
    }

    private fun showDialogToConfirmAccpeptance(enrollmentId: Int?) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_dialog_enrollment_contirmation))
            .setMessage(getString(R.string.alert_dialog_enrollment_acceptance))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                presenter.postAcceptEnrollment(enrollmentId)
                toolbar_progressbar?.visibility = View.VISIBLE
            }.setNeutralButton(getString(R.string.cancel)) { _, _ ->
                finish()
            }.create()
            .show()
    }

    private fun showDialogToConfirmRejection(enrollmentId: Int?) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_dialog_enrollment_contirmation))
            .setMessage(getString(R.string.alert_dialog_enrollment_rejection))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                presenter.postRejectEnrollment(enrollmentId)
                toolbar_progressbar?.visibility = View.VISIBLE
            }.setNeutralButton(getString(R.string.cancel)) { _, _ ->
                finish()
            }.create()
            .show()
    }

    private fun showDialogToConfirmFinish(enrollmentId: Int?) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_dialog_enrollment_contirmation))
            .setMessage(getString(R.string.alert_dialog_enrollment_finish))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                presenter.deleteEnrollment(enrollmentId)
                toolbar_progressbar?.visibility = View.VISIBLE
            }.setNeutralButton(getString(R.string.cancel)) { _, _ ->
                finish()
            }.create()
            .show()
    }

    override fun successfulEnrollment() {
        toolbar_progressbar?.visibility = View.GONE
        finish()
    }

    override fun unsuccessfulFinishEnrollment(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        toolbar_progressbar?.visibility = View.GONE
        unsuccessfulCall(msgRef)
    }

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    override fun getContext() = this

    companion object {
        const val EXTRA_STUDENT_INFO = "EXTRA_STUDENT_INFO"
    }
}