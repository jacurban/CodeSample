package com.jac.caravela.scenes.parentinfo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.User
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.toolbar.*

class ParentInfoActivity : AppCompatActivity(), ParentInfo.View {

    private lateinit var presenter: ParentInfo.Presenter
    private val containerStudentList by lazy { mutableListOf<User>() }
    private var containerStudentAdapter: ContainerStudentAdapter? = null
    private var parentInfo: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        presenter = ParentInfoPresenter(this)
        parentInfo = intent.getParcelableExtra(EXTRA_PARENT_INFO) as User

        setupToolbar()
        setupViewElements()
        setupContainerStudentList()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_editBTN?.visibility = View.GONE
        toolbar_title?.text = parentInfo?.name
    }

    private fun setupViewElements() {
        user_info_birth_TXV?.text = parentInfo?.birthDateString
        user_info_cpf_TXV?.text = parentInfo?.cpf
        user_info_email_TXV?.text = parentInfo?.email
        user_info_phone_TXV?.text = parentInfo?.telephone

        user_info_calendar_group?.visibility = View.GONE
    }

    private fun setupContainerStudentList() {
        containerStudentAdapter = ContainerStudentAdapter(containerStudentList)
        user_info_students_RCV?.adapter = containerStudentAdapter
    }

    override fun successfulGetAll(studentList: List<User>) {
        this.containerStudentList.clear()
        this.containerStudentList.addAll(studentList)
        containerStudentAdapter?.notifyDataSetChanged()
        if (studentList.isEmpty()) {
            user_info_students_group?.visibility = View.GONE
            showShortMessage(getString(R.string.responsible_with_no_students))
        } else {
            user_info_students_group?.visibility = View.VISIBLE
        }
        toolbar_progressbar?.visibility = View.GONE
    }

    override fun unsuccessfulGetAll(msgRef: Int?) {
        toolbar_progressbar?.visibility = View.GONE
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        unsuccessfulCall(msgRef)
    }

    override fun onResume() {
        super.onResume()
        presenter.getStudentByResponsible(parentInfo?.id)
        toolbar_progressbar?.visibility = View.VISIBLE
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_PARENT_INFO = "EXTRA_PARENT_INFO"
    }
}