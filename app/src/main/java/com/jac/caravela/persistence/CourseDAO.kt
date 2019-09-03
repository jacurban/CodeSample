package com.jac.caravela.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jac.caravela.model.Course

@Dao
interface CourseDAO {
    @Insert
    fun insert(course: Course)

    @Query("SELECT * FROM course")
    fun get(): Course?

    @Query("DELETE FROM user")
    fun delete()
}