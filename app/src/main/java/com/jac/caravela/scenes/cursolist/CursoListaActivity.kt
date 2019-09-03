package com.jac.caravela.scenes.cursolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.jac.caravela.model.Course
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.scenes.cursocadastro.CursoCadastroActivity
import com.jac.caravela.scenes.turmalist.TurmaListActivity
import kotlinx.android.synthetic.main.list_fab.*
import kotlinx.android.synthetic.main.toolbar.*

class CursoListaActivity : AppCompatActivity(), CursoLista.View, CourseAdapter.Listener {

    private lateinit var presenter: CursoLista.Presenter
    private val courseList: MutableList<Course> by lazy { mutableListOf<Course>() }
    private var courseAdapter: CourseAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fab)

        presenter = CursoListaPresenter(this)

        setupToolbar()
        setupRecycleView()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        list_fab_toolbarLayout?.visibility = View.VISIBLE
        toolbar_title?.text = getString(R.string.cursos)
    }

    private fun setupRecycleView() {
        courseAdapter = CourseAdapter(courseList, this)
        list_fab_RCV?.adapter = courseAdapter
        list_fab_RCV?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
    }

    private fun setupViewElements() {
        if (App.user?.isTeacher() == true || App.user?.isResponsible() == true)
            list_fab_BTN?.hide()

        list_fab_BTN?.setOnClickListener {
            val intent = Intent(this, CursoCadastroActivity::class.java)
            startActivityForResult(intent, CursoCadastroActivity.REQCODE_FAB)
        }
    }

    override fun successfulGetAll(courseList: List<Course>) {
        this.courseList.clear()
        this.courseList.addAll(courseList)
        courseAdapter?.notifyDataSetChanged()
        hideProgressBar()
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        hideProgressBar()
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        unsuccessfulCall(msgRef)
    }

    override fun goToTurmaListActivity(course: Course) {
        val intent = Intent(this, TurmaListActivity::class.java)
        intent.putExtra(TurmaListActivity.EXTRA_COURSE, course)
        startActivity(intent)
    }

    override fun deleteCourse(courseName: String, courseId: Int) {
        deleteDialogConfirmation(courseName, courseId)
    }

    private fun deleteDialogConfirmation(name: String, id: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_dialog_enrollment_contirmation))
            .setMessage(
                "${getString(R.string.alert_dialog_delete_description2)} " +
                        "${name}, " +
                        "${getString(R.string.alert_dialog_delete_description3)} " +
                        "${getString(R.string.alert_dialog_delete_description4)} " +
                        "${name}?"
            )
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                presenter.deleteCourse(id)
                showProgressBar()
                toolbar_progressbar?.visibility = View.VISIBLE
            }.setNeutralButton(getString(R.string.cancel)) { _, _ ->
            }.create()
            .show()
    }

    private fun showProgressBar() {
        toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        toolbar_progressbar?.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllCourses()
        showProgressBar()
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
    }
}