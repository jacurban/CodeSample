package com.jac.caravela.scenes.turmalist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Classe
import com.jac.caravela.model.Course
import com.jac.caravela.scenes.cursocadastro.CursoCadastroActivity
import com.jac.caravela.scenes.turmacadastro.TurmaCadastroActivity
import com.jac.caravela.scenes.turmainfo.TurmaInfoActivity
import kotlinx.android.synthetic.main.activity_list_turmas.*
import kotlinx.android.synthetic.main.toolbar.*

class TurmaListActivity : AppCompatActivity(), TurmaList.View, TurmaAdapter.Listener {

    private lateinit var presenter: TurmaList.Presenter
    private val turmaList: MutableList<Classe> by lazy { mutableListOf<Classe>() }
    private var turmaAdapter: TurmaAdapter? = null
    private var course: Course? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_turmas)

        presenter = TurmaListPresenter(this)

        course = intent.getParcelableExtra(EXTRA_COURSE)

        setupToolbar()
        setupRecycleView()
        setupViewElements()
    }

    private fun setupRecycleView() {
        turmaAdapter = TurmaAdapter(turmaList, this)
        lista_turmas_RCV?.adapter = turmaAdapter
        lista_turmas_RCV?.layoutManager = LinearLayoutManager(this)
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        if (App.user?.isAdministrator() == true || App.user?.isCoordinator() == true)
            toolbar_editBTN?.visibility = View.VISIBLE

        toolbar_title?.text = course?.name

        toolbar_editBTN?.setOnClickListener {
            val i = Intent(this, CursoCadastroActivity::class.java)
            i.putExtra(CursoCadastroActivity.EXTRA_EDIT, course)
            startActivityForResult(i, App.REQ_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == App.REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                finish()
            }
        }
    }

    private fun setupViewElements() {
        if (App.user?.isTeacher() == true || App.user?.isResponsible() == true)
            lista_turmas_fab?.hide()

        course?.description?.let { lista_turmas_description_TXV?.text = course?.description }

        lista_turmas_fab?.setOnClickListener {
            val intent = Intent(this, TurmaCadastroActivity::class.java)
            intent.putExtra(TurmaCadastroActivity.EXTRA_COURSE, course?.id)
            startActivity(intent)
        }
    }

    override fun successfulGetClassByCourse(courseClasses: List<Classe>) {
        turmaList.clear()
        turmaList.addAll(courseClasses)
        turmaAdapter?.notifyDataSetChanged()
        hideProgressBar()
    }

    override fun unsuccessfulGetClassByCourse(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        hideProgressBar()
        unsuccessfulCall(msgRef)
    }

    override fun goToTurmaInfoActivity(classInfo: Classe, code: Int) {
        val intent = Intent(this, TurmaInfoActivity::class.java)
        intent.putExtra(TurmaInfoActivity.EXTRA_CLASS_INFO, classInfo)
        startActivity(intent)
    }

    private fun showProgressBar() {
        toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        toolbar_progressbar?.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        presenter.getClassesByCourse(course?.id)
        showProgressBar()
    }

    override fun deteleClass(className: String, classId: Int) {
        deleteDialogConfirmation(className, classId)
    }

    private fun deleteDialogConfirmation(name: String, id: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_dialog_enrollment_contirmation))
            .setMessage("${getString(R.string.alert_dialog_delete_description)} " +
                    "${name}?" )
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                presenter.deleteClass(id, course?.id)
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

    companion object {
        const val EXTRA_COURSE = "EXTRA_COURSE"
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
    }
}