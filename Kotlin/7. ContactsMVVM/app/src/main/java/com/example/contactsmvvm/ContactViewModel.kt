package com.example.contactsmvvm

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsmvvm.databinding.AddCntactDialogBinding
import com.example.contactsmvvm.databinding.ContactItemBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepositoryImplementation) :
    ViewModel() {
    fun addContact(contact: Contact) = viewModelScope.launch {
        repository.addContact(contact)
    }

    fun getContact(id: Int) = viewModelScope.launch {
        repository.getContact(id)
    }

    fun updateContact(contact: Contact) = viewModelScope.launch {
        repository.updateContact(contact)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.deleteContact(contact)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun getAllContacts() = repository.getAllContacts()


    fun openDialog(context: Context, contact: Contact? = null) {
        val binding: AddCntactDialogBinding =
            AddCntactDialogBinding.inflate(LayoutInflater.from(context))

        val dialog = Dialog(context)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);


        if (contact != null) {
            binding.contactDialogTitle.text = "Edit Contact"
            binding.addContactButton.text = "Save"

            binding.contact = contact
        }

        binding.addContactButton.setOnClickListener {
            val name = binding.editContactName.text.toString().trim()
            val email = binding.editContactEmail.text.toString().trim()
            val phone = binding.editContactPhone.text.toString().trim()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                if (contact == null) {
                    val contact = Contact(name = name, email = email, phone = phone, avatarUrl = "")
                    addContact(contact)
                } else {
                    val newContact = contact.copy(name = name, email = email, phone = phone)
                    updateContact(newContact)
                }
            } else {
                Toast.makeText(
                    context,
                    "Fill the name and phone please",
                    Toast.LENGTH_LONG
                ).show()
            }
            dialog.dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}