package com.jac.caravela.scenes.matriculalist

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Enrollment
import com.jac.caravela.model.EnrollmentStatus
import com.jac.caravela.scenes.main.MainActivity
import kotlinx.android.synthetic.main.list_fab.*
import kotlinx.android.synthetic.main.toolbar.*

class MatriculaListFragment : Fragment(), MatriculaList.View {

    private lateinit var presenter: MatriculaList.Presenter
    private val matriculaList: MutableList<Enrollment> by lazy { mutableListOf<Enrollment>() }
    private var matriculaListaAdapter: MatriculaListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MatriculaListPresenter(this)

        matriculaListaAdapter = MatriculaListAdapter(matriculaList, activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_fab_BTN?.hide()

        list_fab_RCV?.adapter = matriculaListaAdapter
        list_fab_RCV?.layoutManager = LinearLayoutManager(context)
    }

    override fun successfulGetAll(matriculaList: List<Enrollment>) {
        this.matriculaList.clear()
        this.matriculaList.addAll(matriculaList.filter { it.statusId == EnrollmentStatus.PENDING.value })
        this.matriculaList.addAll(matriculaList.filter { it.statusId == EnrollmentStatus.APPROVED.value })
        this.matriculaList.addAll(matriculaList.filter { it.statusId == EnrollmentStatus.REFUSED.value })
        matriculaListaAdapter?.notifyDataSetChanged()
        hideProgressBar()
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
        presenter.getAllEnrollments()
        showProgressBar()
    }

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val FRAGMENT_TAG = "matriculaList.MatriculaListFragment"

        fun newInstance() = MatriculaListFragment()
    }
}