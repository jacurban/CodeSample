package com.jac.caravela.scenes.partner

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PartnerListPresenter(val view: PartnerList.View) : PartnerList.Presenter() {
    override fun getAllPartner() {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.partnerRepository.getAllPartner() }
            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun deletePartner(id: Int) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.partnerRepository.postDeletePartner(id) }
            if (response.first)
                getAllPartner()
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

}