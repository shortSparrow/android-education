package com.example.retrofitdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitdemo.data.User

class UserListAdapter(private val userList: ArrayList<User>): RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    inner class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userEmail: TextView = itemView.findViewById(R.id.user_email)
        val userPhone: TextView = itemView.findViewById(R.id.user_phone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = userList[position]
        holder.userName.text = user.name
        holder.userEmail.text = user.email
        holder.userPhone.text = user.phone
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}