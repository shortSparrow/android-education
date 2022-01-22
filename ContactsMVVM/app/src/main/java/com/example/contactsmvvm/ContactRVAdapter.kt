package com.example.contactsmvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactRVAdapter(
    var list: List<Contact>,
    private val onClickDeleteIcon: ContactDeleteItemClick,
    private val contactItemClick: ContactItemClick) :
    RecyclerView.Adapter<ContactRVAdapter.ContactViewHolder>() {


    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById<TextView>(R.id.contact_name)
        val email: TextView = view.findViewById<TextView>(R.id.contact_email)
        val phone: TextView = view.findViewById<TextView>(R.id.contact_phone)
        val deleteContact: ImageView = view.findViewById<ImageView>(R.id.delete_contact)
    }


    interface ContactDeleteItemClick {
        fun onClickDeleteIcon(contact: Contact)
    }

    interface ContactItemClick {
        fun onClickContact(contact: Contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return  ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.email.text = list[position].email
        holder.phone.text = list[position].phone

        holder.deleteContact.setOnClickListener{
            onClickDeleteIcon.onClickDeleteIcon(list[position])
        }

        holder.itemView.setOnClickListener{
            contactItemClick.onClickContact(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}