package com.jac.caravela.scenes.turmacadastro

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.jac.caravela.R
import com.jac.caravela.extensions.checkIfIsBlank
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Classe
import com.jac.caravela.model.Lesson
import com.jac.caravela.model.User
import com.jac.caravela.scenes.selectlocation.SelectLocationActivity
import com.jac.caravela.tools.DateTools
import kotlinx.android.synthetic.main.activity_turma_cadastro.*
import kotlinx.android.synthetic.main.activity_user_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class TurmaCadastroActivity : AppCompatActivity(), TurmaCadastro.View, LessonAdapter.Listener {

    private lateinit var presenter: TurmaCadastro.Presenter
    private var selectedLatLng: LatLng? = null
    private val lessons by lazy { mutableListOf<Lesson>() }
    private val teachers by lazy { mutableListOf<User>() }
    private var courseId: Int? = null
    private var date: String? = null
    private var selectedDate: String? = null

    private var lessonAdapter: LessonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turma_cadastro)

        presenter = TurmaCadastroPresenter(this)
        lessonAdapter = LessonAdapter(lessons, this)

        setupToolbar()
        setupSpinners()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_title?.text = getString(R.string.nova_turma)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setupViewElements() {
        turma_cadastro_lessons_RCV?.adapter = lessonAdapter

        courseId = intent.getIntExtra(EXTRA_COURSE, 0)

        val classInfo: Classe? = intent.getParcelableExtra(EXTRA_CLASS_INFO)
        if (classInfo != null) {
            courseId = classInfo.courseId
            toolbar_title?.visibility = View.GONE
            turma_cadastro_save_BTN?.text = getString(R.string.editar)
            turma_cadastro_class_EDT?.setText(classInfo.name)
            turma_cadastro_reference_EDT?.setText(classInfo.localName)
            turma_cadastro_payment_EDT?.setText(classInfo.price)
            val initialDate = SimpleDateFormat("dd/MM/yyyy").format(classInfo.initialDate)
            turma_cadastro_initialdate_EDT?.text = initialDate
            selectedLatLng = LatLng(classInfo.latitude.toDouble(), classInfo.longitude.toDouble())
            map_selection_status?.isActivated = true
            lessons.clear()
            lessons.addAll(classInfo.lessons)
            lessonAdapter?.notifyDataSetChanged()
        } else {
            turma_cadastro_save_BTN?.text = getString(R.string.finish_registration)
            map_selection_status?.isActivated = false
        }

        turma_cadastro_local_BTN?.setOnClickListener {
            val intent = Intent(this, SelectLocationActivity::class.java)
            startActivityForResult(intent, SELECT_LOCATION_REQUEST_CODE)
        }

        turma_cadastro_save_BTN?.isActivated = true

        turma_cadastro_initialdate_EDT?.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedDate = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
                date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(cal.time)
                turma_cadastro_initialdate_EDT?.setText(selectedDate)
            }
            DatePickerDialog(
                this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, dateSetListener, cal.get(
                    Calendar.YEAR
                ), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        turma_cadastro_save_BTN?.setOnClickListener {
            val name = turma_cadastro_class_EDT?.text.toString()
            val reference = turma_cadastro_reference_EDT?.text.toString()
            val teacherId = teachers[turma_cadastro_teacher_SPN.selectedItemPosition].id
            val price = turma_cadastro_payment_EDT?.text.toString()
            val courseId = this.courseId ?: run {
                emptyFieldError()
                return@setOnClickListener
            }
            val latitude = selectedLatLng?.latitude?.toString() ?: run {
                emptyFieldError()
                return@setOnClickListener
            }
            val longitude = selectedLatLng?.longitude?.toString() ?: run {
                emptyFieldError()
                return@setOnClickListener
            }
            if (validFields()
                && teacherId != 0
                && courseId != 0
                && lessons.isNotEmpty()
            ) {
                if (classInfo == null)
                    presenter.postRegisterClass(
                        name, reference, courseId, teacherId, latitude, longitude, lessons, price, date
                    )
                else

                    presenter.postEditClass(
                        classInfo.id, name, reference, courseId, teacherId, latitude, longitude, lessons, price, date
                    )
            }
        }

        turma_cadastro_add_BTN?.setOnClickListener {
            val day = (turma_cadastro_day_SPN?.selectedItemPosition ?: 0) + 1
            val hour = turma_cadastro_hour_SPN?.selectedItem as String
            val minute = turma_cadastro_minute_SPN?.selectedItem as String

            val lesson = if (classInfo == null) Lesson(day = day, hour = hour, minute = minute)
            else Lesson(classId = classInfo.id, day = day, hour = hour, minute = minute)
            lessons.firstOrNull { it == lesson }?.let { } ?: run {
                lessons.add(lesson)
                lessonAdapter?.notifyDataSetChanged()
                turma_cadastro_lessons_RCV?.layoutManager?.scrollToPosition(lessons.size - 1)
            }
        }

        toolbar_progressbar?.visibility = View.VISIBLE
        presenter.getAllTeachers()
    }

    private fun validFields(): Boolean {
        if (turma_cadastro_class_EDT.checkIfIsBlank()
            || turma_cadastro_reference_EDT.checkIfIsBlank()
        )
            return false
        return true
    }

    private fun emptyFieldError() {
        showShortMessage(getString(R.string.error_empty_fields))
    }

    private fun setupSpinners() {
        val arrayAdapterDays = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, DateTools.weekDays)
        val arrayAdapterHours = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, DateTools.dayHours)
        val arrayAdapterMinutes =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, DateTools.hourMinutes)

        turma_cadastro_day_SPN?.adapter = arrayAdapterDays
        turma_cadastro_hour_SPN?.adapter = arrayAdapterHours
        turma_cadastro_minute_SPN?.adapter = arrayAdapterMinutes
    }

    override fun successfulEdit() {
        val i = Intent()
        setResult(Activity.RESULT_OK, i)
        finish()
    }

    override fun successfulRegister() {
        toolbar_progressbar?.visibility = View.GONE
        finish()
    }

    override fun unsuccessfulRegister(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        toolbar_progressbar?.visibility = View.GONE
        unsuccessfulCall(msgRef)
    }

    override fun successfulGetAll(teacherList: List<User>) {
        teachers.clear()
        teachers.addAll(teacherList)
        val arrayAdapterTeacher =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, teacherList.map { it.name })
        turma_cadastro_teacher_SPN?.adapter = arrayAdapterTeacher
        toolbar_progressbar?.visibility = View.GONE
    }

    override fun deteleLesson(position: Int) {
        lessons.removeAt(position)
        lessonAdapter?.notifyDataSetChanged()
    }

    override fun showDeleteLesson() = true

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_LOCATION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val lat = data?.getDoubleExtra(SelectLocationActivity.LATITUDE_EXTRA, 0.0) ?: 0.0
                val lng = data?.getDoubleExtra(SelectLocationActivity.LONGITUDE_EXTRA, 0.0) ?: 0.0
                selectedLatLng = LatLng(lat, lng)
                map_selection_status?.isActivated = true
            } else {
                Log.d("MAP", "erro")
            }
        }
    }

    companion object {
        const val SELECT_LOCATION_REQUEST_CODE = 2
        const val EXTRA_COURSE = "EXTRA_COURSE"
        const val EXTRA_CLASS_INFO = "EXTRA_CLASS_INFO"
    }
}