package com.jac.caravela.scenes.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.scenes.personalinfo.PersonalInfoActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), Main.View, BottomNavigationListeners.Listener {

    private lateinit var presenter: Main.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)

        setupToolbar()
        setupBottomNavigation()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        toolbar_title?.visibility = View.VISIBLE
        toolbar_title?.text = App.user?.name
        toolbar_personBTN?.visibility = View.VISIBLE
        toolbar_personBTN?.setOnClickListener {
            val i = Intent(this, PersonalInfoActivity::class.java)
            i.putExtra(PersonalInfoActivity.EXTRA_PERSONAL_INFO, App.user)
            startActivity(i)
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigationListeners = BottomNavigationListeners(this)
        when {
            App.user?.isAdministrator() == true -> {
                botnav_view?.apply {
                    inflateMenu(R.menu.botnav_adm)
                    setOnNavigationItemSelectedListener(bottomNavigationListeners.admOnNavigationListener)
                    selectedItemId = R.id.adm_navigation_messages
                }
            }
            App.user?.isResponsible() == true -> {
                botnav_view?.apply {
                    inflateMenu(R.menu.botnav_parents)
                    setOnNavigationItemSelectedListener(bottomNavigationListeners.parentOnNavigationListener)
                    selectedItemId = R.id.parents_navigation_map
                }
            }
            App.user?.isTeacher() == true -> {
                botnav_view?.apply {
                    inflateMenu(R.menu.botnav_teacher)
                    setOnNavigationItemSelectedListener(bottomNavigationListeners.teacherOnNavigationListener)
                    selectedItemId = R.id.teacher_navigation_messages
                }
            }
            App.user?.isCoordinator() == true -> {
                botnav_view?.apply {
                    inflateMenu(R.menu.botnav_adm)
                    setOnNavigationItemSelectedListener(bottomNavigationListeners.admOnNavigationListener)
                    selectedItemId = R.id.adm_navigation_messages
                }
            }
        }
    }

    override fun replaceFragmentWith(tag: String, builder: () -> Fragment) = with(supportFragmentManager) {
        transaction {
            findFragmentByTag(tag)?.let {
                replace(R.id.main_container_frl, it, tag)
            } ?: run {
                replace(R.id.main_container_frl, builder.invoke(), tag)
                addToBackStack(tag)
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun getContext() = this

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }
}

