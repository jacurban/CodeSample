package com.jac.caravela.scenes.teacherlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.User
import com.jac.caravela.scenes.teachercadastro.TeacherCadastroActivity
import com.jac.caravela.scenes.usercadastro.UserAdapter
import kotlinx.android.synthetic.main.list_fab.*
import kotlinx.android.synthetic.main.toolbar.*

class TeacherListActivity : AppCompatActivity(), TeacherList.View, UserAdapter.Listener {

    private lateinit var presenter: TeacherList.Presenter
    private val teacherList: MutableList<User> by lazy { mutableListOf<User>() }
    private var teacherAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fab)

        presenter = TeacherListPresenter(this)

        setupToolbar()
        setupRecycleView()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        list_fab_toolbarLayout?.visibility = View.VISIBLE
        toolbar_title?.text = getString(R.string.professores)
    }

    private fun setupRecycleView() {
        teacherAdapter = UserAdapter(teacherList, this)
        list_fab_RCV?.adapter = teacherAdapter
        list_fab_RCV?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
    }

    private fun setupViewElements() {
        list_fab_BTN?.setOnClickListener {
            val intent = Intent(this, TeacherCadastroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun successfulGetAll(teacherList: List<User>) {
        this.teacherList.clear()
        this.teacherList.addAll(teacherList)
        teacherAdapter?.notifyDataSetChanged()
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
        presenter.getAllTeachers()
        showProgressBar()
    }

    override fun deleteUser(userName:String, userId: Int) {
        deleteDialogConfirmation(userName, userId)
    }

    private fun deleteDialogConfirmation(name: String, id: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_dialog_enrollment_contirmation))
            .setMessage("${getString(R.string.alert_dialog_delete_description)} " +
                    "${name}?" )
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