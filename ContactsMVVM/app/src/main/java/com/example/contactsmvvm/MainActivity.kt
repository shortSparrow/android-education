package com.example.contactsmvvm

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ContactRVAdapter.ContactItemClick,
    ContactRVAdapter.ContactDeleteItemClick {
    lateinit var itemsRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    private lateinit var list: List<Contact>
    private lateinit var contactRVAdapter: ContactRVAdapter
    lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsRV = findViewById(R.id.contact_recycler_view)
        addFAB = findViewById(R.id.fab)
        list = ArrayList<Contact>()
        contactRVAdapter = ContactRVAdapter(list, this, this)
        itemsRV.layoutManager = LinearLayoutManager(this)
        itemsRV.adapter = contactRVAdapter
        val contactRepository = ContactRepositoryImplementation(ContactDatabase.invoke(this).dao)
        val contactFactory = ContactViewModelFactory(contactRepository)

        contactViewModel = ViewModelProvider(this, contactFactory)[ContactViewModel::class.java]

        contactViewModel.getAllContacts().observe(this, {
            contactRVAdapter.list = it
            contactRVAdapter.notifyDataSetChanged()
        })

        addFAB.setOnClickListener {
            contactViewModel.openDialog(this)
        }
    }

    override fun onClickDeleteIcon(contact: Contact) {
        contactViewModel.deleteContact(contact)
        contactRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "Contacts has been delete", Toast.LENGTH_LONG).show()
    }

    override fun onClickContact(contact: Contact) {
        contactViewModel.openDialog(this, contact)
    }


}