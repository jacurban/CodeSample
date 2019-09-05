package com.jac.caravela.scenes.studentlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.User
import com.jac.caravela.scenes.usercadastro.UserAdapter
import kotlinx.android.synthetic.main.list_fab.*
import kotlinx.android.synthetic.main.toolbar.*

class StudentListActivity : AppCompatActivity(), StudentList.View, UserAdapter.Listener {

    private lateinit var presenter: StudentList.Presenter
    private val studentList: MutableList<User> by lazy { mutableListOf<User>() }
    private var studentAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fab)

        presenter = StudentListPresenter(this)

        setupToolbar()
        setupRecycleView()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        list_fab_toolbarLayout?.visibility = View.VISIBLE
        toolbar_title?.text = getString(R.string.alunos)
    }

    private fun setupRecycleView() {
        studentAdapter = UserAdapter(studentList, this)
        list_fab_RCV?.adapter = studentAdapter
        list_fab_RCV?.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewElements() {
        list_fab_BTN?.hide()
    }

    override fun successfulGetAll(studentList: List<User>) {
        this.studentList.clear()
        this.studentList.addAll(studentList)
        studentAdapter?.notifyDataSetChanged()
        hideProgressBar()
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        hideProgressBar()
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        unsuccessfulCall(msgRef)
    }

    private fun showProgressBar() {
        toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        toolbar_progressbar?.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllStudents()
        showProgressBar()
    }

    override fun deleteUser(userName: String, userId: Int) {
        deleteDialogConfirmation(userName, userId)
    }

    private fun deleteDialogConfirmation(name: String, id: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_dialog_enrollment_contirmation))
            .setMessage(
                "${getString(R.string.alert_dialog_delete_description)} " +
                        "${name}?"
            )
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                presenter.deleteUser(id)
                showProgressBar()
                toolbar_progressbar?.visibility = View.VISIBLE
            }.setNeutralButton(getString(R.string.cancel)) { _, _ ->
            }.create()
            .show()
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }
}