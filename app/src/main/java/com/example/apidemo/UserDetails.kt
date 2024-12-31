package com.example.apidemo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_details)

        val userId : TextView = findViewById(R.id.UserDetailId)
        val userTitle : TextView = findViewById(R.id.UserDetailTitle)


        val id = intent.getIntExtra("id",0) ?: "0"
        val title = intent.getStringExtra("title",) ?: "Unknown"

        userId.text = id.toString()
        userTitle.text = title

    }
}