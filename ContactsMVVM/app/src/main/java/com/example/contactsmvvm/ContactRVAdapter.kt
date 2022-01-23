package com.example.contactsmvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsmvvm.databinding.ContactItemBinding

class ContactRVAdapter(
    var list: List<Contact>,
    private val onClickDeleteIcon: ContactDeleteItemClick,
    private val contactItemClick: ContactItemClick
) :
    RecyclerView.Adapter<ContactRVAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private var binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.contact = contact
            binding.deleteContact.setOnClickListener {
                onClickDeleteIcon.onClickDeleteIcon(contact)
            }
        }
    }


    interface ContactDeleteItemClick {
        fun onClickDeleteIcon(contact: Contact)
    }

    interface ContactItemClick {
        fun onClickContact(contact: Contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ContactItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            contactItemClick.onClickContact(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}