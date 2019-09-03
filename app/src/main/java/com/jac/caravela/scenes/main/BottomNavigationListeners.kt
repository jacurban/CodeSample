package com.jac.caravela.scenes.main

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jac.caravela.R
import com.jac.caravela.scenes.cadastromenu.CadastroMenuFragment
import com.jac.caravela.scenes.calendarioteacher.CalendarioTeacherFragment
import com.jac.caravela.scenes.chatlist.ChatListFragment
import com.jac.caravela.scenes.eventolist.EventoListFragment
import com.jac.caravela.scenes.matriculalist.MatriculaListFragment
import com.jac.caravela.scenes.parentstudent.ParentStudentFragment
import com.jac.caravela.scenes.parentstudentsection.ParentStudentSectionFrag
import com.jac.caravela.scenes.relatorio.RelatorioFragment
import com.jac.caravela.scenes.turmamap.TurmaMapFragment

class BottomNavigationListeners(listener: Listener) {

    val parentOnNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.parents_navigation_map -> {
                listener.replaceFragmentWith(TurmaMapFragment.FRAGMENT_TAG) {
                    TurmaMapFragment.newInstance()
                }
                true
            }
            R.id.parents_navigation_messages -> {
                listener.replaceFragmentWith(ChatListFragment.FRAGMENT_TAG) {
                    ChatListFragment.newInstance()
                }
                true
            }
            R.id.parents_navigation_students -> {
                listener.replaceFragmentWith(ParentStudentSectionFrag.FRAGMENT_TAG) {
                    ParentStudentSectionFrag.newInstance()
                }
                true
            }
            R.id.parents_navigation_events -> {
                listener.replaceFragmentWith(EventoListFragment.FRAGMENT_TAG) {
                    EventoListFragment.newInstance()
                }
                true
            }
            else -> false
        }
    }

    val teacherOnNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.teacher_navigation_messages -> {
                listener.replaceFragmentWith(ChatListFragment.FRAGMENT_TAG) {
                    ChatListFragment.newInstance()
                }
                true
            }
            R.id.teacher_navigation_classes -> {
                listener.replaceFragmentWith(CalendarioTeacherFragment.FRAGMENT_TAG) {
                    CalendarioTeacherFragment.newInstance()
                }
                true
            }
            R.id.teacher_navigation_events -> {
                listener.replaceFragmentWith(EventoListFragment.FRAGMENT_TAG) {
                    EventoListFragment.newInstance()
                }
                true
            }
            else -> false
        }
    }

    val admOnNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.adm_navigation_messages -> {
                listener.replaceFragmentWith(ChatListFragment.FRAGMENT_TAG) {
                    ChatListFragment.newInstance()
                }
                true
            }
            R.id.adm_navigation_reports -> {
                listener.replaceFragmentWith(RelatorioFragment.FRAGMENT_TAG) {
                    RelatorioFragment.newInstance()
                }
                true
            }
            R.id.adm_navigation_cadastro -> {
                listener.replaceFragmentWith(CadastroMenuFragment.FRAGMENT_TAG) {
                    CadastroMenuFragment.newInstance()
                }
                true
            }
            R.id.adm_navigation_registrations -> {
                listener.replaceFragmentWith(MatriculaListFragment.FRAGMENT_TAG) {
                    MatriculaListFragment.newInstance()
                }
                true
            }
            R.id.adm_navigation_events -> {
                listener.replaceFragmentWith(EventoListFragment.FRAGMENT_TAG) {
                    EventoListFragment.newInstance()
                }
                true
            }
            else -> false
        }
    }

    interface Listener {
        fun replaceFragmentWith(tag: String, builder: () -> Fragment)
    }
}