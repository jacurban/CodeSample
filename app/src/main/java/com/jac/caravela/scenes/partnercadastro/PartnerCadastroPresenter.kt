package com.jac.caravela.scenes.partnercadastro

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PartnerCadastroPresenter(val view: PartnerCadastro.View) : PartnerCadastro.Presenter() {
    override fun postRegisterPartner(name: String, cnpj: String, email: String, description: String?) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.partnerRepository.postRegisterPartner(name, cnpj, email, description)
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

    override fun postEditPartner(id: Int, name: String, cnpj: String, email: String, description: String?) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.partnerRepository.postEditPartner(id, name, cnpj, email, description)
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

}