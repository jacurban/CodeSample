package com.jac.caravela.scenes.partner

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Partner
import com.jac.caravela.scenes.partnercadastro.PartnerCadastroActivity
import kotlinx.android.synthetic.main.list_fab.*
import kotlinx.android.synthetic.main.toolbar.*


class PartnerListActivity : AppCompatActivity(), PartnerList.View, PartnerAdapter.Listener {

    private lateinit var presenter: PartnerList.Presenter
    private val partnerList: MutableList<Partner> by lazy { mutableListOf<Partner>() }
    private var partnerAdapter: PartnerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fab)

        presenter = PartnerListPresenter(this)

        setupToolbar()
        setupRecycleView()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        list_fab_toolbarLayout?.visibility = View.VISIBLE
        toolbar_title?.text = getString(R.string.parceiros)
    }

    private fun setupRecycleView() {
        partnerAdapter = PartnerAdapter(partnerList, this)
        list_fab_RCV?.adapter = partnerAdapter
        list_fab_RCV?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
    }

    private fun setupViewElements() {
        if (App.user?.isTeacher() == true || App.user?.isResponsible() == true)
            list_fab_BTN?.hide()

        list_fab_BTN?.setOnClickListener {
            val intent = Intent(this, PartnerCadastroActivity::class.java)
            startActivity(intent)
        }
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

    override fun deletePartner(partnerName: String, partnerId: Int) {
        deleteDialogConfirmation(partnerName, partnerId)
    }

    private fun deleteDialogConfirmation(name: String, id: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_dialog_enrollment_contirmation))
            .setMessage("${getString(R.string.alert_dialog_delete_description)} " +
                        "${name}?" )
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                presenter.deletePartner(id)
                showProgressBar()
                toolbar_progressbar?.visibility = View.VISIBLE
            }.setNeutralButton(getString(R.string.cancel)) { _, _ ->
            }.create()
            .show()
    }


    override fun openActivity(intent: Intent) {
        startActivity(intent)
    }

    override fun showAdmOptions() = true

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_NEW_COURSE = "EXTRA_NEW_COURSE"
        const val EXTRA_COURSE_EDIT = "EXTRA_COURSE_EDIT"
    }
}