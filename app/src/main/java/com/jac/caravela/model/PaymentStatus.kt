package com.jac.caravela.model

enum class PaymentStatus(val value: Int) {
    AUTHORIZED(1),
    WAITINGPAYMENT(2),
    PROCESSING(3),
    PAID(4),
    REFUNDED(5),
    PENDINGREFUND(6),
    REFUSED(7);
}