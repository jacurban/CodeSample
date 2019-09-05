package com.jac.caravela.scenes.turmamap

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.jac.caravela.R
import com.jac.caravela.extensions.showShortMessage
import com.jac.caravela.model.Classe
import com.jac.caravela.scenes.main.MainActivity
import com.jac.caravela.scenes.turmainfo.TurmaInfoActivity
import kotlinx.android.synthetic.main.custom_marker.view.*
import kotlinx.android.synthetic.main.toolbar.*


class TurmaMapFragment : Fragment(), TurmaMap.View, OnMapReadyCallback {

    private lateinit var presenter: TurmaMap.Presenter
    private val classes by lazy { mutableListOf<Classe>() }

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = TurmaMapPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_turma_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllClasses()
        showProgressBar()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        googleMap.setOnMarkerClickListener {
            val intent = Intent(activity, TurmaInfoActivity::class.java)
            intent.putExtra(TurmaInfoActivity.EXTRA_CLASS_INFO, it.tag as Classe)
            intent.putExtra(TurmaInfoActivity.EXTRA_SUBSCRIPTION_WAY, 10)
            startActivity(intent)
            true
        }
    }

    override fun generateCustomMarkerToClass(courseName: String, classeName: String): Bitmap {
        val customMarker = this.layoutInflater.inflate(R.layout.custom_marker, null, false) as LinearLayout
        customMarker.custom_marker_curso_TV.text = courseName
        customMarker.custom_marker_turma_TV.text = classeName
        customMarker.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        customMarker.layout(0, 0, customMarker.measuredWidth, customMarker.measuredHeight)

        customMarker.isDrawingCacheEnabled = true
        customMarker.buildDrawingCache()
        return customMarker.drawingCache
    }

    override fun successfulGetAllClasses(response: List<Classe>) {
        hideProgressBar()
        classes.clear()
        classes.addAll(response)
        map?.let { presenter.onMapReady(it, resources.displayMetrics.widthPixels, classes) }
    }

    override fun unsuccessfulGetAllClasses(msgRef: Int?) {
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

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val FRAGMENT_TAG = "turmasMap.MapFragment"

        fun newInstance() = TurmaMapFragment()
    }
}