package com.jac.caravela.scenes.eventolist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Evento
import kotlinx.android.synthetic.main.item_evento_mapa.view.*
import java.text.SimpleDateFormat


class EventoAdapter(private val eventos: MutableList<Evento>, private val listener: Listener) :
    RecyclerView.Adapter<EventoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento_mapa, parent, false)

        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return eventos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventos[position])
    }

    class ViewHolder(view: View, private val listener: Listener) : RecyclerView.ViewHolder(view) {

        fun bind(evento: Evento) {
            setupViewElements(evento)
            setupMap(evento)
        }

        @SuppressLint("SimpleDateFormat")
        private fun setupViewElements(evento: Evento){
            val eventDate = SimpleDateFormat("dd MMMM").format(evento.date)
            val eventTime = SimpleDateFormat("HH:mm'h'").format(evento.date)
            val datetime = "$eventDate - $eventTime"
            val reference = "(${evento.local})"

            itemView.item_eventos_name_TXV?.text = evento.name
            itemView.item_eventos_date_TXV?.text = datetime
            itemView.item_eventos_description_TXV?.text = evento.description
            itemView.item_eventos_reference_TXV?.text = reference

            if (evento.description == null)
                itemView.item_eventos_description_TXV.visibility = View.GONE

            if (App.user?.isAdministrator() == true || App.user?.isCoordinator() == true)
                itemView.item_event_delete_BTN.visibility = View.VISIBLE

            itemView.item_event_delete_BTN.setOnClickListener {
                listener.deleteEvent(evento.id)
            }
        }

        private fun setupMap(evento: Evento) {
            var mapCurrent: GoogleMap
            val onMapReadyCallback = OnMapReadyCallback { googleMap ->
                MapsInitializer.initialize(listener.getContext())
                mapCurrent = googleMap
                mapCurrent.uiSettings?.setAllGesturesEnabled(false)

                val latitude = evento.latitude.toDouble()
                val longitude = evento.longitude.toDouble()
                val latLng = LatLng(latitude, longitude)
                mapCurrent.apply {
                    clear()
                    addMarker(MarkerOptions().position(latLng))
                    moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                }
            }
            val map = itemView.findViewById(R.id.item_event_map) as MapView
            map.onCreate(null)
            map.onResume()
            map.getMapAsync(onMapReadyCallback)
        }
    }

    interface Listener {
        fun getContext(): Context?
        fun deleteEvent(id: Int)
    }
}