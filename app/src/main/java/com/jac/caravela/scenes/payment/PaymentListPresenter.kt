package com.jac.caravela.scenes.payment

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentListPresenter(val view: PaymentList.View) : PaymentList.Presenter() {
    override fun getPaymentsByResponsible(responsibleId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.paymentRepository.getPaymentsByResponsible(responsibleId)
            }
            if (response.first)
                response.third?.let { view.successfulPayByResponsible(it)}
            else
                view.unsuccessfulGet(response.second)
        }
    }

}