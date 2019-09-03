package com.jac.caravela.scenes.turmamap

import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.jac.caravela.model.Classe
import com.jac.caravela.scenes.Scene

interface TurmaMap {
    interface View: Scene.View {
        fun generateCustomMarkerToClass(courseName: String, classeName: String): Bitmap
        fun successfulGetAllClasses(response: List<Classe>)
        fun unsuccessfulGetAllClasses (msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun onMapReady(map: GoogleMap, width: Int, classes: List<Classe>)
        abstract fun getAllClasses()
    }
}