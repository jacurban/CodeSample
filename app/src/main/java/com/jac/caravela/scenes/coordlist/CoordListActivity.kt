package com.jac.caravela.scenes.coordlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.User
import com.jac.caravela.scenes.coordcadastro.CoordCadastroActivity
import com.jac.caravela.scenes.usercadastro.UserAdapter
import kotlinx.android.synthetic.main.list_fab.*
import kotlinx.android.synthetic.main.toolbar.*

class CoordListActivity : AppCompatActivity(), CoordList.View, UserAdapter.Listener {

    private lateinit var presenter: CoordList.Presenter
    private val coordList: MutableList<User> by lazy { mutableListOf<User>()}
    private var coordAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fab)

        presenter = CoordListPresenter(this)

        setupToolbar()
        setupRecycleView()
        setupViewElements()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        list_fab_toolbarLayout?.visibility = View.VISIBLE
        toolbar_title?.text = getString(R.string.coordenadores)
    }

    private fun setupRecycleView() {
        coordAdapter = UserAdapter(coordList, this)
        list_fab_RCV?.adapter = coordAdapter
        list_fab_RCV?.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewElements() {
        list_fab_BTN?.setOnClickListener {
            val intent = Intent(this, CoordCadastroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun successfulGetAll(coordList: List<User>) {
        this.coordList.clear()
        this.coordList.addAll(coordList)
        coordAdapter?.notifyDataSetChanged()
        hideProgressBar()
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

    override fun onResume() {
        super.onResume()
        presenter.getAllCoordinators()
        showProgressBar()
    }
    override fun deleteUser(userName:String, userId: Int) {
        deleteDialogConfirmation(userName, userId)
    }

    private fun deleteDialogConfirmation(name: String, id: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_dialog_enrollment_contirmation))
            .setMessage("${getString(R.string.alert_dialog_delete_description)} " +
                    "${name}?" )
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                presenter.deleteUser(id)
                showProgressBar()
                toolbar_progressbar?.visibility = View.VISIBLE
            }.setNeutralButton(getString(R.string.cancel)) { _, _ ->
            }.create()
            .show()
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_NEW_COORD = "EXTRA_NEW_COORD"
    }
}