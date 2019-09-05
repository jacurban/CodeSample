package com.jac.caravela.scenes.eventolist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Evento
import com.jac.caravela.scenes.eventocadastro.EventoCadastroActivity
import com.jac.caravela.scenes.main.MainActivity
import kotlinx.android.synthetic.main.list_fab.*
import kotlinx.android.synthetic.main.toolbar.*

class EventoListFragment : Fragment(), EventoList.View, EventoAdapter.Listener {

    private lateinit var presenter: EventoList.Presenter
    private val eventList: MutableList<Evento> by lazy { mutableListOf<Evento>() }
    private var eventAdapter: EventoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = EventoListPresenter(this)

        eventAdapter = EventoAdapter(eventList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (App.user?.isResponsible() == true || App.user?.isTeacher() == true)
            list_fab_BTN?.hide()

        list_fab_RCV?.adapter = eventAdapter
        list_fab_RCV?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        list_fab_BTN?.setOnClickListener {
            val intent = Intent(activity, EventoCadastroActivity::class.java)
            startActivityForResult(intent, EventoCadastroActivity.REQCODE_NEW_EVENT)
        }
    }

    override fun successfulGetAll(eventList: List<Evento>) {
        this.eventList.clear()
        this.eventList.addAll(eventList)
        eventAdapter?.notifyDataSetChanged()
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

    override fun deleteEvent(id: Int) {
        presenter.deleteEvent(id)
        showProgressBar()
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllEvents()
        showProgressBar()
    }

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val FRAGMENT_TAG = "eventos.EventosFragment"

        fun newInstance() = EventoListFragment()
    }
}