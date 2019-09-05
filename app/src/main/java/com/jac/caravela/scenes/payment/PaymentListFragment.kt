package com.jac.caravela.scenes.payment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Payment
import com.jac.caravela.scenes.main.MainActivity
import kotlinx.android.synthetic.main.list_fab.*
import kotlinx.android.synthetic.main.toolbar.*

class PaymentListFragment : Fragment(), PaymentList.View, PaymentListAdapter.Listener {

    private lateinit var presenter: PaymentList.Presenter
    private val paymentList by lazy { mutableListOf<Payment>() }
    private var paymentAdapter: PaymentListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = PaymentListPresenter(this)
        paymentAdapter = PaymentListAdapter(paymentList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_fab_BTN?.hide()
        list_fab_RCV?.adapter = paymentAdapter
    }

    override fun onResume() {
        super.onResume()
        presenter.getPaymentsByResponsible(App.user?.id)
        showProgressBar()
    }

    override fun successfulPayByResponsible(payments: List<Payment>) {
        paymentList.clear()
        paymentList.addAll(payments)
        paymentAdapter?.notifyDataSetChanged()
        hideProgressBar()
    }

    override fun unsuccessfulGet(msgRef: Int?) {
        hideProgressBar()
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        unsuccessfulCall(msgRef)    }

    private fun showProgressBar() {
        (activity as MainActivity).toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        (activity as MainActivity).toolbar_progressbar?.visibility = View.GONE
    }

    override fun goToPaymentPage(link: String) {
        val uris = Uri.parse(link)
        val openURL = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        openURL.putExtras(b)
        startActivity(openURL)
    }
}