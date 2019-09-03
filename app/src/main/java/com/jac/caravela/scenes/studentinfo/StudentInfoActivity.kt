package com.jac.caravela.scenes.studentinfo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Classe
import com.jac.caravela.model.Lesson
import com.jac.caravela.model.User
import com.jac.caravela.scenes.calendarioteacher.CalendarioAdapter
import com.jac.caravela.scenes.studentcadastro.StudentCadastroActivity
import com.jac.caravela.scenes.turmainfo.TurmaInfoActivity
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.toolbar.*

class StudentInfoActivity : AppCompatActivity(), StudentInfo.View, CalendarioAdapter.Listener {

    private lateinit var presenter: StudentInfo.Presenter
    private lateinit var calendarAdapter: CalendarioAdapter
    private var studentInfo: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        presenter = StudentInfoPresenter(this)
        calendarAdapter = CalendarioAdapter(this)

        studentInfo = intent.getParcelableExtra(EXTRA_STUDENT_INFO)

        setupToolbar()
        setupRecycleView()
        setupViewElements()
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar)

        toolbar_title?.text = studentInfo?.name

        if (App.user?.isResponsible() == true) {
            toolbar_editBTN?.visibility = View.VISIBLE

            toolbar_editBTN?.setOnClickListener{
                val intent = Intent(this, StudentCadastroActivity::class.java)
                intent.putExtra(StudentCadastroActivity.EXTRA_STUDENT_EDIT, studentInfo)
                startActivityForResult(intent, App.REQ_CODE)
            }
        }
    }

    private fun setupRecycleView(){
        user_info_calendar_RCV?.adapter = calendarAdapter
        user_info_calendar_RCV?.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewElements(){
        user_info_students_group?.visibility = View.GONE
        
        if(App.user?.isTeacher() == true)
            user_info_calendar_group?.visibility = View.GONE


        user_info_birth_TXV?.text = studentInfo?.birthDateString
        user_info_cpf_TXV?.text = studentInfo?.cpf
        user_info_email_TXV?.text = studentInfo?.email
        user_info_phone_TXV?.text = studentInfo?.telephone
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == App.REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.getUser(studentInfo?.id)
                showProgressBar()
            }
        }
    }

    override fun successfulGetUser(user: User) {
        hideProgressBar()
        studentInfo = user
        setupToolbar()
        setupViewElements()
    }

    override fun successfulGetAll(classList: List<Classe>) {
        hideProgressBar()
        val lessons = mutableListOf<Lesson>()
        classList.forEachIndexed { index, classe ->
            classe.lessons.forEach { lesson -> lesson.classId = index }
            lessons.addAll(classe.lessons)
        }
        calendarAdapter.classes = classList
        calendarAdapter.lessons = lessons
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        hideProgressBar()
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        unsuccessfulCall(msgRef)
    }

    override fun onResume() {
        super.onResume()
        presenter.getClassByStudent(studentInfo?.id)
        showProgressBar()
    }

    private fun showProgressBar() {
        toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        toolbar_progressbar?.visibility = View.GONE
    }

    override fun getContext() = this

    override fun goToTurmaInfoActivity(classInfo: Classe) {
        val intent = Intent(this, TurmaInfoActivity::class.java)
        intent.putExtra(TurmaInfoActivity.EXTRA_CLASS_INFO, classInfo)
        startActivity(intent)
    }

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_STUDENT_INFO = "EXTRA_STUDENT_INFO"
    }
}