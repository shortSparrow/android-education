package com.example.getdatafromshopinglist

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
            // Cursor це ітератор по базі даних, за яйого допомогою ми можемо пройтись по всій базі даних
            val cursor = contentResolver.query(
                Uri.parse("content://com.example.shoppinglist/shop_items"),
                null,
                null,
                null,
                null,
                null,
            )

            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("count"))
                val enabled =
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")) > 0 // у Cursor намає метода getBoolean, тож такий дивний спосіб

                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled,
                )
                Log.d("MainActivityXXX", shopItem.toString())
            }

            cursor?.close() // Ми маємо закрити cursor після відкриття
        }
    }
}