package com.jac.caravela.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Message (
    var senderId: Int,
    var senderName: String?,
    var receiverId: Int?,
    var receiverName: String?,
    var description: String,
    var sendDate: Date
): Parcelable