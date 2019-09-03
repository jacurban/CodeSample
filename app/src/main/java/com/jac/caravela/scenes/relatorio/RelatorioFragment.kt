package com.jac.caravela.scenes.relatorio

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Report
import com.jac.caravela.scenes.main.MainActivity
import com.jac.caravela.tools.DateTools
import kotlinx.android.synthetic.main.fragment_relatorio.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class RelatorioFragment : Fragment(), Relatorio.View {

    private lateinit var presenter: Relatorio.Presenter
    private val reportList by lazy { mutableListOf<Report>() }
    private lateinit var reportAdapter: RelatorioAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = RelatorioPresenter(this)
        reportAdapter = RelatorioAdapter(reportList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_relatorio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewElements()
        setupDateSpinners()
        setupReportTypesSpinner()
        setupReportButton()
    }

    private fun setupDateSpinners() {
        val years = mutableListOf<Int>()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        for (year in FIRST_YEAR..currentYear) {
            years.add(year)
        }
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

        val adapterStartMonth =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, DateTools.months)
        val adapterStartYear = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, years)
        val adapterEndMonth =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, DateTools.months)
        val adapterEndYear = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, years)

        relatorio_start_month_SPN?.apply {
            adapter = adapterStartMonth
            setSelection(currentMonth)
        }
        relatorio_end_month_SPN?.apply {
            adapter = adapterEndMonth
            setSelection(currentMonth)
        }
        relatorio_start_year_SPN?.apply {
            adapter = adapterStartYear
            setSelection(years.size - 1)
        }
        relatorio_end_year_SPN?.apply {
            adapter = adapterEndYear
            setSelection(years.size - 1)
        }
    }

    private fun setupReportTypesSpinner() {
        val reportTypes = resources.getStringArray(R.array.report_types)
        val adapterReportType =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, reportTypes)

        relatorio_type_SPN?.adapter = adapterReportType
    }

    private fun setupReportButton() {
        relatorio_report_BTN?.setOnClickListener {
            val format = SimpleDateFormat("yyyy/MM/dd")
            val calendar = Calendar.getInstance()

            val startMonth = (relatorio_start_month_SPN?.selectedItemPosition ?: 0)
            val startYear = (relatorio_start_year_SPN?.selectedItem as Int)
            val endMonth = (relatorio_end_month_SPN?.selectedItemPosition ?: 0)
            val endYear = (relatorio_end_year_SPN?.selectedItem as Int)

            if ((startYear > endYear) || ((startYear == endYear) && (startMonth >= endMonth)))
                showShortMessage(getString(R.string.error_report_wrong_date))
            else {
                calendar.set(startYear, startMonth, 1, 0, 0)
                val startDate = format.format(calendar.time)
                calendar.set(endYear, endMonth, 1, 0, 0)
                val endDate = format.format(calendar.time)

                showProgressBar()
                when (relatorio_type_SPN?.selectedItemPosition) {
                    0 -> presenter.getReportEnrollments(startDate, endDate)
                    1 -> presenter.getReportClasses(startDate, endDate)
                    2 -> presenter.getReportPosts(startDate, endDate)
                    3 -> presenter.getReportMessages(startDate, endDate)
                    4 -> presenter.getReportResponsibles(startDate, endDate)
                    5 -> presenter.getReportStudents(startDate, endDate)
                }
            }
        }
    }

    private fun setupViewElements() {
        relatorio_RCV?.adapter = reportAdapter
    }

    override fun successfulGetReport(reportList: List<Report>) {
        relatorio_RCV?.visibility = View.VISIBLE
        this.reportList.clear()
        this.reportList.addAll(reportList)
        hideProgressBar()
        if (reportList.isEmpty()) {
            relatorio_RCV?.visibility = View.GONE
            showShortMessage(getString(R.string.report_no_item))
        }
        else
            reportAdapter.notifyDataSetChanged()
    }

    override fun unsuccessfulGetReport(msgRef: Int?) {
        val msg = getString(msgRef ?: R.string.error_unspecified)
        showShortMessage(msg)
        hideProgressBar()
        unsuccessfulCall(msgRef)
    }

    private fun showProgressBar() {
        (activity as MainActivity).toolbar_progressbar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        (activity as MainActivity).toolbar_progressbar?.visibility = View.GONE
    }


    companion object {
        const val FIRST_YEAR = 2019
        const val FRAGMENT_TAG = "relatorio.RelatoriosFragment"

        fun newInstance() = RelatorioFragment()
    }
}