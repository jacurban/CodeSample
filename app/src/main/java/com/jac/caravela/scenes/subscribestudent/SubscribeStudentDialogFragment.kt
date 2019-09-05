package com.jac.caravela.scenes.subscribestudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Classe
import com.jac.caravela.model.User
import kotlinx.android.synthetic.main.dialog_subscribe_student.*
import kotlinx.android.synthetic.main.toolbar.*

class SubscribeStudentDialogFragment : BottomSheetDialogFragment(), SubscribeStudent.View,
    SubscribeStudentAdapter.Listener {

    private lateinit var presenter: SubscribeStudent.Presenter
    private var studentListAdapter: SubscribeStudentAdapter? = null
    private val studentList by lazy { mutableListOf<User>() }
    private var classInfo: Classe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Design_Light_BottomSheetDialog)

        presenter = SubscribeStudentPresenter(this)
        classInfo = arguments?.getParcelable(CLASS_EXTRA)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.dialog_subscribe_student, container)
        studentListAdapter = SubscribeStudentAdapter(studentList, this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe_student_RCV?.adapter = studentListAdapter
        App.user?.id?.let {
            presenter.getStudentByResponsible(it)
            subscribe_student_PB?.visibility = View.VISIBLE
        }
    }

    override fun successfulGetAll(students: List<User>) {
        subscribe_student_PB?.visibility = View.GONE
        if (students.isEmpty()) {
            showShortMessage(getString(R.string.error_no_students))
            dismiss()
        } else {
            this.studentList.clear()
            this.studentList.addAll(students)
            studentListAdapter?.notifyDataSetChanged()
        }
    }

    override fun successfulRegister() {
        showDialogEnrollmentInformation()
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        subscribe_student_PB?.visibility = View.GONE
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        unsuccessfulCall(msgRef)
    }

    override fun showAlertDialogToConfirmEnrollment(student: User) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.alert_dialog_confirm_enrollment_title))
                .setMessage(
                    "${getString(R.string.alert_dialog_enrollment_description_1)} " +
                            "${student.name} " +
                            "${getString(R.string.alert_dialog_enrollment_description_2)} " +
                            "${classInfo?.name} " +
                            "${getString(R.string.alert_dialog_enrollment_description_3)} " +
                            "${classInfo?.courseName}?")
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    toolbar_progressbar?.visibility = View.VISIBLE
                    presenter.postRegisterEnrollment(student.id, classInfo?.id)
                }.setNeutralButton(getString(R.string.cancel)) { _, _ ->
                    dismiss()
                }.create()
                .show()
        }
    }

    private fun showDialogEnrollmentInformation(){
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.alert_dialog_atention_title))
                .setMessage(getString(R.string.alert_dialog_enrollment_information))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    toolbar_progressbar?.visibility = View.GONE
                    activity?.finish()
                }.create()
                .show()
        }
    }

    companion object {
        const val FRAGMENT_TAG = "studentInfo.StudentSubsDialogFragment"
        private const val CLASS_EXTRA = "class_extra"

        fun newInstance(turma : Classe?) = SubscribeStudentDialogFragment().apply {
            val args = Bundle()
            args.putParcelable(CLASS_EXTRA, turma)
            arguments = args
        }
    }
}