package com.jac.caravela.scenes.calendarioteacher

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Classe
import com.jac.caravela.model.Lesson
import com.jac.caravela.scenes.main.MainActivity
import com.jac.caravela.scenes.turmainfo.TurmaInfoActivity
import kotlinx.android.synthetic.main.fragment_calendario_turmas.*
import kotlinx.android.synthetic.main.toolbar.*


class CalendarioTeacherFragment : Fragment(), CalendarioTeacher.View, CalendarioAdapter.Listener {

    private lateinit var presenter: CalendarioTeacher.Presenter

    private var calendarAdapter: CalendarioAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CalendarioTeacherPresenter(this)

        calendarAdapter = CalendarioAdapter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calendario_turmas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar_RCV?.adapter = calendarAdapter
        calendar_RCV?.layoutManager = LinearLayoutManager(requireContext())
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
        (activity as MainActivity).toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        (activity as MainActivity).toolbar_progressbar?.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        presenter.getClassesByTeacher(App.user?.id)
        showProgressBar()
    }

    override fun goToTurmaInfoActivity(classInfo: Classe) {
        val intent = Intent(activity, TurmaInfoActivity::class.java)
        intent.putExtra(TurmaInfoActivity.EXTRA_CLASS_INFO, classInfo)
        startActivity(intent)
    }

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val FRAGMENT_TAG = "minhasTurmas.CalendarioTurmasFragment"

        fun newInstance() = CalendarioTeacherFragment()
    }
}