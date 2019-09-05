package com.jac.caravela.scenes.turmainfo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Classe
import com.jac.caravela.model.Lesson
import com.jac.caravela.model.User
import com.jac.caravela.scenes.subscribestudent.SubscribeStudentDialogFragment
import com.jac.caravela.scenes.turmacadastro.LessonAdapter
import com.jac.caravela.scenes.turmacadastro.TurmaCadastroActivity
import com.jac.caravela.scenes.turmatimeline.TurmaTimelineActivity
import kotlinx.android.synthetic.main.activity_turma_info.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat

class TurmaInfoActivity :
    AppCompatActivity(),
    TurmaInfo.View,
    LessonAdapter.Listener,
    ClassStudentListAdapter.Listener,
    OnMapReadyCallback {

    private lateinit var presenter: TurmaInfo.Presenter

    private var map: GoogleMap? = null

    private var classInfo: Classe? = null
    private var studentId: Int = -1
    private var subscriptionCode: Int = -1

    private val lessons by lazy { mutableListOf<Lesson>() }
    private val students by lazy { mutableListOf<User>() }

    private var lessonAdapter: LessonAdapter? = null
    private var userAdapter: ClassStudentListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turma_info)

        presenter = TurmaInfoPresenter(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.turma_info_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        lessonAdapter = LessonAdapter(lessons, this)
        userAdapter = ClassStudentListAdapter(students, this)

        classInfo = intent.getParcelableExtra(EXTRA_CLASS_INFO)
        subscriptionCode = intent.getIntExtra(EXTRA_SUBSCRIPTION_WAY, -1)
        studentId = intent.getIntExtra(EXTRA_STUDENT_ID, -1)

        setupToolbar()
        setupViewElements()
    }

    override fun onResume() {
        super.onResume()
        classInfo?.id?.let { presenter.getStudentsByClass(it) }
        toolbar_progressbar?.visibility = View.VISIBLE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        classInfo?.apply {
            val latLng = LatLng(latitude.toDouble(), longitude.toDouble())
            map?.clear()
            map?.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(courseName)
                    .snippet(name)
            )
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_timelineBTN?.visibility = View.VISIBLE

        if (App.user?.isResponsible() == true && subscriptionCode == SUBSCRIPTION_CODE) {
            turma_info_register_BTN?.visibility = View.VISIBLE
            toolbar_timelineBTN?.visibility = View.GONE
        } else {
            turma_info_register_BTN?.visibility = View.GONE
            toolbar_timelineBTN?.visibility = View.VISIBLE
        }

        if (App.user?.isAdministrator() == true || App.user?.isCoordinator() == true)
            toolbar_editBTN?.visibility = View.VISIBLE

        toolbar_editBTN?.setOnClickListener {
            val intent = Intent(this, TurmaCadastroActivity::class.java)
            intent.putExtra(TurmaCadastroActivity.EXTRA_CLASS_INFO, classInfo)
            startActivityForResult(intent, App.REQ_CODE)
        }

        toolbar_timelineBTN?.setOnClickListener {
            val intent = Intent(this, TurmaTimelineActivity::class.java)
            intent.putExtra(TurmaTimelineActivity.EXTRA_CLASS_INFO, classInfo)
            startActivity(intent)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setupViewElements() {
        toolbar_title?.text = classInfo?.name

        turma_info_teacher_TXV?.text = classInfo?.teacherName
        turma_info_reference_TXV?.text = classInfo?.localName
        turma_info_course_TXV?.text = classInfo?.courseName
        turma_info_payment_TXV?.text = classInfo?.price

        classInfo?.initialDate?.let {
            turma_info_start_TXV?.text = SimpleDateFormat("dd/MM/yyyy").format(it)
        }

        turma_info_lessons_RCV?.adapter = lessonAdapter
        turma_info_students_RCV?.adapter = userAdapter
        
        turma_info_register_BTN?.isActivated = true

        if (classInfo?.lessons.isNullOrEmpty())
            turma_info_lessons_group.visibility = View.GONE
        else {
            lessons.clear()
            lessons.addAll(classInfo?.lessons ?: emptyList())
            lessonAdapter?.notifyDataSetChanged()
        }

        turma_info_register_BTN?.setOnClickListener {
            SubscribeStudentDialogFragment.newInstance(classInfo)
                .show(supportFragmentManager, SubscribeStudentDialogFragment.FRAGMENT_TAG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == App.REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.getClass(classInfo?.id)
            }
        }
    }

    override fun successfulGetClass(classe: Classe?) {
        classInfo = classe
        setupViewElements()
    }

    override fun successfulGetAll(students: List<User>) {
        this.students.clear()
        this.students.addAll(students)
        userAdapter?.notifyDataSetChanged()
        toolbar_progressbar?.visibility = View.GONE

        if (students.isEmpty()) {
            turma_info_students_group?.visibility = View.GONE
            showShortMessage(getString(R.string.no_enrolled_student))
        } else
            turma_info_students_group?.visibility = View.VISIBLE
    }

    override fun unsuccessfulRequest(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        toolbar_progressbar?.visibility = View.GONE
        unsuccessfulCall(msgRef)
    }

    override fun deteleLesson(position: Int) {}

    override fun showDeleteLesson() = false

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_CLASS_INFO = "EXTRA_CLASS_INFO"
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
        const val EXTRA_SUBSCRIPTION_WAY = "EXTRA_SUBSCRIPTION_WAY"
        const val SUBSCRIPTION_CODE = 10
    }
}