package com.shannon.memething

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var auth: FirebaseAuth
        auth = Firebase.auth

        loginTabLayout.addOnTabSelectedListener(object:
            TabLayout.OnTabSelectedListener {

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text?.equals(getString(R.string.sign_up))!!) { toggleToSignUp() }
                else if (tab.text?.equals(getString(R.string.login))!!) { toggleToLogin() }
            }
        })
    }

    private fun toggleToSignUp() {
        loginScreenNameTextField.visibility = View.VISIBLE
        loginConfirmPasswordTextField.visibility = View.VISIBLE
        resetPasswordText.visibility = View.GONE
    }

    private fun toggleToLogin() {
        loginScreenNameTextField.visibility = View.GONE
        loginConfirmPasswordTextField.visibility = View.GONE
        resetPasswordText.visibility = View.VISIBLE
    }
}
