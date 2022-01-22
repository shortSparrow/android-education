package com.example.contactsmvvm

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.add_cntact_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )

        val addButton = dialog.findViewById<Button>(R.id.add_contact_button)
        val cancelButton = dialog.findViewById<Button>(R.id.cancel_button)

        val nameField = dialog.findViewById<EditText>(R.id.edit_contact_name)
        val emailField = dialog.findViewById<EditText>(R.id.edit_contact_email)
        val phoneField = dialog.findViewById<EditText>(R.id.edit_contact_phone)

        if (contact != null) {
            dialog.findViewById<TextView>(R.id.contact_dialog_title).text = "Edit Contact"
            dialog.findViewById<TextView>(R.id.add_contact_button).text = "Save"

            nameField.setText(contact.name)
            emailField.setText(contact.email)
            phoneField.setText(contact.phone)
        }

        addButton.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val phone = phoneField.text.toString().trim()

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

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}