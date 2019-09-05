package com.jac.caravela.scenes.eventocadastro

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.jac.caravela.R
import com.jac.caravela.extensions.checkIfIsBlank
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.scenes.selectlocation.SelectLocationActivity
import kotlinx.android.synthetic.main.activity_evento_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class EventoCadastroActivity : AppCompatActivity(), EventoCadastro.View {

    private lateinit var presenter: EventoCadastro.Presenter
    private var selectedLatLng: LatLng? = null
    private var datetime: String? = null
    private var selectedDate: String? = null
    private var selectedTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento_cadastro)

        presenter = EventoCadastroPresenter(this)

        setupToolbar()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_title?.text = getString(R.string.novo_evento)
    }

    private fun setupViewElements() {
        evento_cadastro_save_BTN?.isActivated = true

        evento_cadastro_local_BTN?.setOnClickListener {
            val intent = Intent(this, SelectLocationActivity::class.java)
            startActivityForResult(intent, SELECT_LOCATION_REQUEST_CODE)
        }

        timepickerBTN?.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                selectedTime = SimpleDateFormat("HH:mm").format(cal.time)
                datetime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(cal.time)
                timepickerBTN?.text = selectedTime
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        datepickerBTN?.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedDate = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
                datetime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(cal.time)
                datepickerBTN?.text = selectedDate
            }
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        evento_cadastro_save_BTN?.setOnClickListener {
            val eventName = evento_cadastro_name_EDT?.text.toString()
            val eventDescription = evento_cadastro_information_EDT?.text.toString()
            val eventReference = evento_cadastro_reference_EDT?.text.toString()
            val eventDate = datetime ?: run {
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

            if (
                validFields()
                && selectedTime != null
                && selectedDate != null
            ) {
                presenter.postRegisterEvent(eventName, eventDate, eventReference, latitude, longitude, eventDescription)

                toolbar_progressbar?.visibility = View.VISIBLE

                finish()
            } else
                emptyFieldError()
        }
    }

    private fun validFields(): Boolean {
        if (evento_cadastro_name_EDT.checkIfIsBlank()
            || evento_cadastro_information_EDT.checkIfIsBlank()
            || evento_cadastro_reference_EDT.checkIfIsBlank())
            return false

        return true
    }

    private fun emptyFieldError() {
        showShortMessage(getString(R.string.error_empty_fields))
    }

    override fun successfulRegister() {
        finish()
    }

    override fun unsuccessfulRegister(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        toolbar_progressbar?.visibility = View.GONE
        unsuccessfulCall(msgRef)
    }

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
                evento_cadastro_location_status?.isActivated = true
            } else {
                Log.d("MAP", "erro")
            }
        }
    }

    companion object {
        const val SELECT_LOCATION_REQUEST_CODE = 2
        const val REQCODE_NEW_EVENT = 1
    }
}