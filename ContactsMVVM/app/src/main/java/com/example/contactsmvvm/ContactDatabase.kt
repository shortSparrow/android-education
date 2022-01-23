package com.example.contactsmvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract val dao: ContactDAO

    companion object {
        @Volatile
        private var instance: ContactDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context): ContactDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ContactDatabase::class.java,
                "contacts"
            ).build()
        }
    }


//    companion object {
//        @Volatile
//        private var INSTANCE: ContactDatabase? = null
//
//        fun getDatabase(context: Context): ContactDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context,
//                    ContactDatabase::class.java,
//                    "app_database")
//                    .createFromAsset("database/bus_schedule.db")
//                    .build()
//                INSTANCE = instance
//
//                instance
//            }
//        }
//    }

}