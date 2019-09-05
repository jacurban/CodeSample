package com.jac.caravela.scenes.cadastromenu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.scenes.coordlist.CoordListActivity
import com.jac.caravela.scenes.cursolist.CursoListaActivity
import com.jac.caravela.scenes.parentlist.ParentListActivity
import com.jac.caravela.scenes.partner.PartnerListActivity
import com.jac.caravela.scenes.studentlist.StudentListActivity
import com.jac.caravela.scenes.teacherlist.TeacherListActivity
import kotlinx.android.synthetic.main.fragment_cadastro_menu.*

class CadastroMenuFragment : Fragment(), CadastroMenu.View {

    private lateinit var presenter: CadastroMenu.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CadastroMenuPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cadastro_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (App.user?.isCoordinator() == true) {
            frag_cadastro_coord_BTN?.visibility = View.GONE
            frag_cadastro_partners_BTN?.visibility = View.GONE
        }

        frag_cadastro_parent_BTN?.setOnClickListener {
            val intent1 = Intent(activity, ParentListActivity::class.java)
            startActivity(intent1)
        }

        frag_cadastro_student_BTN?.setOnClickListener {
            val intent2 = Intent(activity, StudentListActivity::class.java)
            startActivity(intent2)
        }

        frag_cadastro_teacher_BTN?.setOnClickListener {
            val intent3 = Intent(activity, TeacherListActivity::class.java)
            startActivity(intent3)
        }

        frag_cadastro_coord_BTN?.setOnClickListener {
            val intent4 = Intent(activity, CoordListActivity::class.java)
            startActivity(intent4)
        }

        frag_cadastro_classes_BTN?.setOnClickListener {
            val intent5 = Intent(activity, CursoListaActivity::class.java)
            startActivity(intent5)
        }

        frag_cadastro_partners_BTN.setOnClickListener {
            val intent7 = Intent(activity, PartnerListActivity::class.java)
            startActivity(intent7)
        }
    }

    override fun onDestroy() {
        presenter.kill()
        super.onDestroy()
    }

    companion object {
        const val FRAGMENT_TAG = "cadastro.CadastroMenuFragment"

        fun newInstance() = CadastroMenuFragment()
    }
}