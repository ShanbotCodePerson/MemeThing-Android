package com.shannon.memething

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // Customize the welcome label
        welcomeLabel.text = "Welcome, ${UserController.currentUser?.screenName}!"
    }

    fun newGameButtonTapped(view: View) {

    }

    fun gamesButtonTapped(view: View) {

    }

    fun friendsButtonTapped(view: View) {

    }

    fun profileButtonTapped(view: View) {
        intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
}
