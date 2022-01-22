package com.example.contactsmvvm

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun addContact(contact: Contact)

    suspend fun getContact(id: Int): Contact?

    suspend fun updateContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun deleteAll()

    fun getAllContacts(): LiveData<List<Contact>>
}