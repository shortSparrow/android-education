package com.example.contactsmvvm

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContact(contact: Contact)

    @Query("select * from contacts")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("select * from contacts where id = :id")
    suspend fun getContact(id: Int): Contact

    @Query("delete from contacts")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)
}