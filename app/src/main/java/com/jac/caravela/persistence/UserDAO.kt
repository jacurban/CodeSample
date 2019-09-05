package com.jac.caravela.persistence

import androidx.room.*
import com.jac.caravela.model.User

@Dao
interface UserDAO {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user")
    fun get(): User?

    @Query("DELETE FROM user")
    fun delete()
}