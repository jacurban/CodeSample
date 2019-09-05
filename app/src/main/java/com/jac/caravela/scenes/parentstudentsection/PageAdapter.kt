package com.jac.caravela.scenes.parentstudentsection

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jac.caravela.R
import com.jac.caravela.scenes.parentstudent.ParentStudentFragment
import com.jac.caravela.scenes.payment.PaymentListFragment


class PagerAdapter(fm: FragmentManager, private val activity: FragmentActivity?) : FragmentPagerAdapter(fm) {

    private val parentStudentFragment: ParentStudentFragment by lazy { ParentStudentFragment() }
    private val paymentFragment: PaymentListFragment by lazy { PaymentListFragment() }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> parentStudentFragment
            1 -> paymentFragment
            else -> parentStudentFragment
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> activity?.getString(R.string.meus_alunos)
            1 -> activity?.getString(R.string.pagamentos)
            else -> activity?.getString(R.string.meus_alunos)
        }
    }
}