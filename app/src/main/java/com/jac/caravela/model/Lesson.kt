package com.jac.caravela.model

import android.os.Parcelable
import com.jac.caravela.tools.DateTools
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lesson(
    var id: Int? = null,
    var classId: Int? = null,
    var day: Int,
    var hour: String,
    var minute: String
) : Parcelable {
    @IgnoredOnParcel
    var showDayOfWeek = false

    fun getDayAsString(): String {
        return DateTools.weekDays[day - 1]
    }

    override fun equals(other: Any?) = ((other is Lesson)
            && (other.day == day)
            && (other.hour == hour)
            && (other.minute == minute))

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (classId ?: 0)
        result = 31 * result + day
        result = 31 * result + hour.hashCode()
        result = 31 * result + minute.hashCode()
        result = 31 * result + showDayOfWeek.hashCode()
        return result
    }
}
