package com.shannon.memething

import android.content.Intent
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

    // to do - replace toasts with alerts

    var auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginTabLayout.addOnTabSelectedListener(object:
            TabLayout.OnTabSelectedListener {

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text?.equals(getString(R.string.sign_up))!!) { toggleToSignUp() }
                else if (tab.text?.equals(getString(R.string.login))!!) { toggleToLogin() }
            }
        })

        // Try to log the user in automatically
        autoLogin()
    }

    private fun toggleToSignUp() {
        loginScreenNameTextField.visibility = View.VISIBLE
        loginConfirmPasswordTextField.visibility = View.VISIBLE
        resetPasswordText.visibility = View.GONE

        loginSignUpButton.text = getString(R.string.sign_up)
    }

    private fun toggleToLogin() {
        loginScreenNameTextField.visibility = View.GONE
        loginConfirmPasswordTextField.visibility = View.GONE
        resetPasswordText.visibility = View.VISIBLE

        loginSignUpButton.text = getString(R.string.login)
    }

    fun resetPasswordButtonTapped(view: View) {
        // TO DO - can present a text field alert if they haven't already entered an email

        // Make sure there's an email address entered
        var email = loginEmailTextField.text.toString()
        if (email.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return
        }

        // Resend the password
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Sent an email to $email. Please allow a few minutes for the email to arrive", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.i("ERROR", "Error in resetPasswordButtonTapped in MainActivity: ${it.toString()}")
                Toast.makeText(applicationContext, "Error sending email to $email - please try again later", Toast.LENGTH_SHORT).show()
            }
    }

    fun doneButtonTapped(view: View) {
        // Make sure there is valid text in the email and password fields
        // need to "present alerts to user if email and password aren't entered")
        var email = loginEmailTextField.text.toString()
        var password = loginPasswordTextField.text.toString()

        if (email.isNullOrEmpty()) {
            Toast.makeText(this, "You must enter a valid email address", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.isNullOrEmpty()) {
            Toast.makeText(this, "You must enter a password", Toast.LENGTH_SHORT).show()
            return
        }

        if (loginTabLayout.selectedTabPosition == 0) { signUp(email, password) }
        else { login(email, password) }
    }

    // Try to log the user in
    private fun autoLogin() {
        auth.currentUser?.let { user ->

            if (!user.isEmailVerified) {
                return
            }

            user.email?.let { email ->
                UserController.fetchUser(email) { success ->
                    // If the user was fetched correctly, transition to the main menu
                    if (success) { toMainMenu() }
                    else {
                        // Otherwise, print and display the error
                        Log.i("ERROR", "Error in autoLogin() in MainActivity")
                        Toast.makeText(
                            applicationContext,
                            "Data could not be fetcehd from cloud - try again later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    // Check that the username and password are valid and fetch the user
    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                auth.currentUser?.let { user ->

                    // Make sure the email is verified
                    // to do - if not, show the alert controller
                    if (!user.isEmailVerified) {

                    } else {
                        // Try to fetch the current user
                        UserController.fetchUser(email) { success ->
                            // If the user was fetched correctly, transition to the main menu
                            if (success) { toMainMenu() }
                            else {
                                // Otherwise, print and display the error
                                Log.i("ERROR", "Error in login() in MainActivity")
                                Toast.makeText(
                                    applicationContext,
                                    "Data could not be fetched from cloud - try again later",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
            .addOnFailureListener {
                // Print and display the error
                Log.i("ERROR", it.toString())
                Toast.makeText(applicationContext, "Error logging in: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

    // Check that all the fields are valid and create a new user
    private fun signUp(email: String, password: String) {
        // Make sure the screen name contains an actual string
        var screenName = loginScreenNameTextField.text.toString()
        if (screenName.isNullOrEmpty()) {
            Toast.makeText(this, "You must enter a username", Toast.LENGTH_SHORT).show()
            return
        }

        // Make sure the passwords match
        if (!password.equals(loginConfirmPasswordTextField.text.toString())) {
            Toast.makeText(this, "Passwords do not match - please try again", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the user and send the notification email
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Send an email to verify the user's email address
                auth.currentUser?.sendEmailVerification()
                    ?.addOnSuccessListener {
                        // Finish setting up the account
                        setUpUser(email, screenName)

                        // Present an alert asking them to check their email
                        Toast.makeText(applicationContext, "An email has been sent to $email - please verify your email", Toast.LENGTH_SHORT).show()
                    }
                    ?.addOnFailureListener {
                        Log.i("ERROR", "Error in signUp() in MainActivity: ${it.toString()}")
                        Toast.makeText(applicationContext, "Error creating account - please try again later", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Log.i("ERROR", "Error in signUp() in MainActivity: ${it.toString()}")
                Toast.makeText(applicationContext, "Error creating account - please try again later", Toast.LENGTH_SHORT).show()
            }
    }

    // Once a user has verified their email, finish completing their account
    private fun setUpUser(email: String, name: String) {

    }

    // Transition to the main menu once the user has signed up or logged in
    private fun toMainMenu() {
        intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }
}