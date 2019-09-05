package com.jac.caravela.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jac.caravela.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class DataBaseClient : RoomDatabase() {

    abstract fun userDAO(): UserDAO

    companion object {
        fun buildDatabase(context: Context) = Room
            .databaseBuilder(
                context,
                DataBaseClient::class.java,
                "caravela.db"
            ).allowMainThreadQueries()
            .build()
    }
}