package com.example.contactsmvvm

import androidx.lifecycle.LiveData

class ContactRepositoryImplementation(private val dao: ContactDAO) : ContactRepository {

    override suspend fun addContact(contact: Contact) {
        dao.addContact(contact)
    }

    override suspend fun getContact(id: Int): Contact {
        return dao.getContact(id)
    }

    override suspend fun updateContact(contact: Contact) {
        dao.updateContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        dao.deleteContact(contact)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override fun getAllContacts(): LiveData<List<Contact>> {
        return dao.getAllContacts()
    }
}