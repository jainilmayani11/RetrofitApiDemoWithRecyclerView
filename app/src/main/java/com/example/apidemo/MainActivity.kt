package com.example.apidemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {

    lateinit var myAdapter: MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var userList: ArrayList<MyDataItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvUser)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        userList = arrayListOf<MyDataItem>()


        getMyData()

        myAdapter = MyAdapter(baseContext, userList)
        recyclerView.adapter = myAdapter
    }


    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(p0: Call<List<MyDataItem>?>, p1: Response<List<MyDataItem>?>) {
                val responseBody = p1.body()!!

                myAdapter = MyAdapter(baseContext, responseBody)
                myAdapter.notifyDataSetChanged()
                recyclerView.adapter = myAdapter



                myAdapter.onItemClick = { myDataItem ->
                    val intent = Intent(this@MainActivity, UserDetails::class.java)
                    intent.putExtra("id", myDataItem.id)
                    intent.putExtra("title", myDataItem.title)
                    startActivity(intent)
                }

            }

            override fun onFailure(p0: Call<List<MyDataItem>?>, p1: Throwable) {
                Log.d("MainActivity", "onFailure: ${p1.message}")
            }
        })
    }
}