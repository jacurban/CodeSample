package com.jac.caravela.scenes.personalinfo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Partner
import com.jac.caravela.scenes.coordcadastro.CoordCadastroActivity
import com.jac.caravela.scenes.parentcadastro.ParentCadastroActivity
import com.jac.caravela.scenes.partner.PartnerAdapter
import com.jac.caravela.scenes.studentcadastro.StudentCadastroActivity
import com.jac.caravela.scenes.teachercadastro.TeacherCadastroActivity
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.toolbar.*

class PersonalInfoActivity : AppCompatActivity(), PersonalInfo.View, PartnerAdapter.Listener {

    private lateinit var presenter: PersonalInfo.Presenter
    private val partnerList: MutableList<Partner> by lazy { mutableListOf<Partner>() }
    private var partnerAdapter: PartnerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        presenter = PersonalInfoPresenter(this)

        setupToolbar()
        setupViewElements()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_editBTN?.visibility = View.VISIBLE
        toolbar_title?.text = App.user?.name

        toolbar_editBTN?.setOnClickListener {
            when (App.user?.type) {
                1 -> {
                    val intent = Intent(this, CoordCadastroActivity::class.java)
                    intent.putExtra(CoordCadastroActivity.EXTRA_ADM_EDIT, App.user)
                    startActivityForResult(intent, CoordCadastroActivity.REQCODE_EDIT)
                }
                2 -> {
                    val intent = Intent(this, ParentCadastroActivity::class.java)
                    intent.putExtra(ParentCadastroActivity.EXTRA_PARENT_EDIT, App.user)
                    startActivityForResult(intent, ParentCadastroActivity.REQCODE_EDIT )
                }
                3 -> {
                    val intent = Intent(this, TeacherCadastroActivity::class.java)
                    intent.putExtra(TeacherCadastroActivity.EXTRA_TEACHER_EDIT, App.user)
                    startActivityForResult(intent, TeacherCadastroActivity.REQCODE_EDIT)
                }
                4 -> {
                    val intent = Intent(this, CoordCadastroActivity::class.java)
                    intent.putExtra(CoordCadastroActivity.EXTRA_COORD_EDIT, App.user)
                    startActivityForResult(intent, CoordCadastroActivity.REQCODE_EDIT)
                }
                5 -> {
                    val intent = Intent(this, StudentCadastroActivity::class.java)
                    intent.putExtra(StudentCadastroActivity.EXTRA_STUDENT_EDIT, App.user)
                    startActivityForResult(intent, StudentCadastroActivity.REQCODE_EDIT)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == App.REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.getUser(App.user?.id)
                showProgressBar()
            }
        }
    }

    private fun setupRecyclerView() {
        partnerAdapter = PartnerAdapter(partnerList, this)
        personal_info_RCV?.adapter = partnerAdapter
        personal_info_RCV?.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewElements() {
//        if (!App.user?.birthDateString.isNullOrEmpty()) {
//            birth_group.visibility = View.VISIBLE
//            personal_info_birth_TXV?.text = App.user?.birthDateString
//        }
//        else
//            birth_group.visibility = View.GONE
//
//        if (!App.user?.telephone.isNullOrEmpty()) {
//            phone_group.visibility = View.VISIBLE
//            personal_info_phone_TXV?.text = App.user?.telephone
//        }
//        else
//            phone_group.visibility = View.GONE

        personal_info_phone_TXV?.text = App.user?.telephone
        personal_info_birth_TXV?.text = App.user?.birthDateString
        personal_info_cpf_TXV?.text = App.user?.cpf
        personal_info_email_TXV?.text = App.user?.email

        personal_info_logout_BTN?.setOnClickListener {
            presenter.logout()
        }
    }

    override fun successfulGetUser() {
        setupToolbar()
        setupViewElements()
    }

    override fun successfulGetAll(partnerList: List<Partner>) {
        this.partnerList.clear()
        this.partnerList.addAll(partnerList)
        partnerAdapter?.notifyDataSetChanged()
        hideProgressBar()
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        hideProgressBar()
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
    }

    private fun showProgressBar() {
        toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        toolbar_progressbar?.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()

        presenter.getAllPartner()
        showProgressBar()
    }

    override fun getContext() = this

    override fun deletePartner(partnerName: String, partnerId: Int) {}
    override fun openActivity(intent: Intent) {}
    override fun showAdmOptions() = false

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_PERSONAL_INFO = "EXTRA_PERSONAL_INFO"
    }
}