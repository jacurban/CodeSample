package com.jac.caravela.scenes.teacherinfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Classe
import com.jac.caravela.model.Lesson
import com.jac.caravela.model.User
import com.jac.caravela.scenes.calendarioteacher.CalendarioAdapter
import com.jac.caravela.scenes.turmainfo.TurmaInfoActivity
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.toolbar.*

class TeacherInfoActivity : AppCompatActivity(), TeacherInfo.View, CalendarioAdapter.Listener {

    private lateinit var presenter: TeacherInfo.Presenter
    private var teacherInfo: User? = null

    private var calendarAdapter: CalendarioAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        presenter = TeacherInfoPresenter(this)

        teacherInfo = intent.getParcelableExtra(EXTRA_TEACHER_INFO)

        calendarAdapter = CalendarioAdapter(this)
        user_info_calendar_RCV?.adapter = calendarAdapter
        user_info_calendar_RCV?.layoutManager = LinearLayoutManager(this)

        setupToolbar()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_editBTN?.visibility = View.GONE
        toolbar_title?.text = teacherInfo?.name
    }

    private fun setupViewElements() {
        user_info_students_group?.visibility = View.GONE
        user_info_calendar_group?.visibility = View.VISIBLE

        user_info_birth_TXV?.text = teacherInfo?.birthDateString
        user_info_cpf_TXV?.text = teacherInfo?.cpf
        user_info_email_TXV?.text = teacherInfo?.email
        user_info_phone_TXV?.text = teacherInfo?.telephone
    }

    override fun goToTurmaInfoActivity(classInfo: Classe) {
        val intent = Intent(this, TurmaInfoActivity::class.java)
        intent.putExtra(TurmaInfoActivity.EXTRA_CLASS_INFO, classInfo)
        startActivity(intent)
    }

    override fun successfulGetAll(classList: List<Classe>) {
        hideProgressBar()
        val lessons = mutableListOf<Lesson>()
        classList.forEachIndexed { index, classe ->
            classe.lessons.forEach { lesson -> lesson.classId = index }
            lessons.addAll(classe.lessons)
        }
        calendarAdapter?.classes = classList
        calendarAdapter?.lessons = lessons
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

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        presenter.getClassesByTeacher(teacherInfo?.id)
        showProgressBar()
    }

    companion object {
        const val EXTRA_TEACHER_INFO = "EXTRA_TEACHER_INFO"
    }
}