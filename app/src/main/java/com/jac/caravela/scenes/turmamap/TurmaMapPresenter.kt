package com.jac.caravela.scenes.turmamap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jac.caravela.core.App
import com.jac.caravela.model.Classe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TurmaMapPresenter (val view: TurmaMap.View): TurmaMap.Presenter() {

    override fun onMapReady(map: GoogleMap, width: Int, classes: List<Classe>) {
        launch(Dispatchers.Main) {
            map.uiSettings.isZoomControlsEnabled = true

            if (classes.isNotEmpty()) {
                val builder = LatLngBounds.Builder()
                map.clear()
                classes.forEach { classe ->
                    val markerOptions = MarkerOptions()

                    markerOptions.position(classe.getLagLng())
                        .icon(
                            BitmapDescriptorFactory.fromBitmap(
                                view.generateCustomMarkerToClass(
                                    classe.courseName,
                                    classe.name
                                )
                            )
                        )

                    val marker = map.addMarker(markerOptions)
                    marker.tag = classe
                    builder.include(marker.position)
                }

                val bounds = builder.build()

                val padding = (width * 0.20).toInt() // offset from edges of the map in pixels
                val cameraUpdateFactory = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                map.animateCamera(cameraUpdateFactory)
            }
        }
    }

    override fun getAllClasses() {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.classRepository.getAllClasses() }
            if (response.first)
                response.third?.let { view.successfulGetAllClasses(it) }
            else
                view.unsuccessfulGetAllClasses(response.second)
        }
    }

}