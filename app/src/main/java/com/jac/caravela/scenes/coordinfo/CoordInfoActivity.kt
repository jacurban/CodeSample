package com.jac.caravela.scenes.coordinfo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.User
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.toolbar.*

class CoordInfoActivity : AppCompatActivity(), CoordInfo.View {

    private lateinit var presenter: CoordInfo.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        presenter = CoordInfoPresenter(this)

        setupViewElements()
    }

    private fun setupViewElements() {
        setSupportActionBar(toolbar)

        if (App.user?.isAdministrator() == true || App.user?.isCoordinator() == true)
            toolbar_editBTN?.visibility = View.VISIBLE

        val coordInfo: User = intent.getParcelableExtra(EXTRA_COORD_INFO)
        user_info_birth_TXV?.text = coordInfo.birthDateString
        user_info_cpf_TXV?.text = coordInfo.cpf
        user_info_phone_TXV?.text = coordInfo.telephone
        user_info_email_TXV?.text = coordInfo.email
        toolbar_title?.text = coordInfo.name

        user_info_students_group?.visibility = View.GONE
        user_info_calendar_group?.visibility = View.GONE
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_COORD_INFO = "EXTRA_COORD_INFO"
    }
}