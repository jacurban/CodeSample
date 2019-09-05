package com.jac.caravela.scenes.parentstudent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.User
import com.jac.caravela.scenes.main.MainActivity
import com.jac.caravela.scenes.studentcadastro.StudentCadastroActivity
import com.jac.caravela.scenes.usercadastro.UserAdapter
import kotlinx.android.synthetic.main.list_fab.*
import kotlinx.android.synthetic.main.toolbar.*

class ParentStudentFragment : Fragment(), ParentStudent.View, UserAdapter.Listener {

    private lateinit var presenter: ParentStudent.Presenter
    private val parentStudentList: MutableList<User> by lazy { mutableListOf<User>() }
    private var parentStudentAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ParentStudentPresenter(this)

        parentStudentAdapter = UserAdapter(parentStudentList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_fab_RCV?.adapter = parentStudentAdapter
        list_fab_RCV?.layoutManager = LinearLayoutManager(activity)

        list_fab_BTN?.setOnClickListener {
            val i = Intent(activity, StudentCadastroActivity::class.java)
            startActivity(i)
        }
    }

    override fun successfulGetStudentByResponsible(students: List<User>) {
        this.parentStudentList.clear()
        this.parentStudentList.addAll(students)
        parentStudentAdapter?.notifyDataSetChanged()
        hideProgressBar()
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        hideProgressBar()
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        unsuccessfulCall(msgRef)
    }

    private fun showProgressBar() {
        (activity as MainActivity).toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        (activity as MainActivity).toolbar_progressbar?.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        presenter.getStudentByResponsible(App.user?.id)
        showProgressBar()
    }

    override fun deleteUser(userName: String, userId: Int) {
        deleteDialogConfirmation(userName, userId)
    }

    private fun deleteDialogConfirmation(name: String, id: Int) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.alert_dialog_enrollment_contirmation))
                .setMessage("${getString(R.string.alert_dialog_delete_description)} " +
                        "${name}?" )
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    presenter.deleteUser(id, App.user?.id)
                    showProgressBar()
                    toolbar_progressbar?.visibility = View.VISIBLE
                }.setNeutralButton(getString(R.string.cancel)) { _, _ ->
                }.create()
                .show()
        }
    }

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_NEW_STUDENT = "EXTRA_NEW_STUDENT"
        const val FRAGMENT_TAG = "alunoResponsavel.ParentStudentFragment"

        fun newInstance() = ParentStudentFragment()
    }
}