package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitdemo.data.User
import com.example.retrofitdemo.service.RetrofitInstance
import com.example.retrofitdemo.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var userList: ArrayList<User>
    lateinit var userListRecyclerView: RecyclerView
    lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        getUsers()
    }

    private fun getUsers() {
        val userService: UserService = RetrofitInstance.service
        val call: Call<ArrayList<User>>  = userService.getUsers()

        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                val users: ArrayList<User>? = response.body()

                if (users != null) {
                    userList = users
//                    for(user in users) {
//                        Log.d("CurrentUSER", user.name.toString())
//                    }
                    fillRecyclerView()
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun fillRecyclerView() {
        userListRecyclerView = findViewById(R.id.user_recycler_view)
        userListAdapter = UserListAdapter(userList)
        userListRecyclerView.layoutManager = LinearLayoutManager(this)
        userListRecyclerView.adapter = userListAdapter
    }
}