package com.jac.caravela.scenes.parentstudentsection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.jac.caravela.R
import kotlinx.android.synthetic.main.fragment_parent_student_section.*

class ParentStudentSectionFrag : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parent_student_section, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = PagerAdapter(childFragmentManager, activity)
        viewpager?.adapter = pagerAdapter

        tab_layout.setupWithViewPager(viewpager)
        tab_layout.tabMode = TabLayout.MODE_FIXED
    }

    companion object{
        const val FRAGMENT_TAG = "students.ParentStudentSectionFrag"

        fun newInstance() = ParentStudentSectionFrag()
    }

}