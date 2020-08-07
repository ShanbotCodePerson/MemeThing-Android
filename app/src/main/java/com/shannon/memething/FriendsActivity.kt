package com.shannon.memething

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_friends.*

class FriendsActivity : AppCompatActivity() {

    // Properties

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ThreeLabelsRecyclerAdapter

    // Lifecyle methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        setUpViews()
    }

    fun setUpViews() {
        title = "Friends"

        linearLayoutManager = LinearLayoutManager(this)
        friendsRecyclerView.layoutManager = linearLayoutManager

        UserController.fetchUsersFriends { users ->
            if (users == null) {
                // Print and display the error
                Log.i("ERROR", "Error in fetchUsersFriends in FriendsActivity")
                Toast.makeText(applicationContext, "Unable to fetch data from cloud", Toast.LENGTH_SHORT).show()
            } else {
                adapter = ThreeLabelsRecyclerAdapter(users)
                friendsRecyclerView.adapter = adapter
            }
        }
    }
}
