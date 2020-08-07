package com.shannon.memething

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.alert_with_text_field.*

class ProfileActivity : AppCompatActivity() {

    // Properties

    var auth: FirebaseAuth = Firebase.auth

    // Lifecycle Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setUpViews()
    }

    // Set Up UI

    fun setUpViews() {
        title = "Profile"

        profileEmailLabel.text = "Email: ${UserController.currentUser?.email}"
        profileScreenNameLabel.text = "Screen Name: ${UserController.currentUser?.screenName}"
        profilePointsLabel.text = "Points: ${UserController.currentUser?.points}"

        // Make the profile photo round
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        profilePhotoCardView.radius = (displayMetrics.widthPixels * 0.6).toFloat()
    }

    // Actions

    fun editProfilePhotoButtonTapped(view: View) {

    }

    fun resetPasswordButtonTapped(view: View) {
        showTextFieldAlert("Enter Current Password", "First enter your current password",
            "Enter Password Here", true) {
            
            Toast.makeText(applicationContext, "Alert worked and text entered was $it", Toast.LENGTH_SHORT).show()
        }
    }

    fun editScreenNameButtonTapped(view: View) {

    }

    fun pointsInfoButtonTapped(view: View) {
        Alerts.showBasicAlert("Points", "Earn points by having your captions chosen in games with your friends", context = this)
    }

    fun deleteAccountButtonTapped(view: View) {

    }

    fun signOutButtonTapped(view: View) {
        // Sign the user out
        auth.signOut()

        // Return to the login screen
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    // A text field alert to get input from the user
    fun showTextFieldAlert(title: String, message: String, hint: String, isPassword: Boolean = false,
                           confirmButtonText: String = "Confirm", cancelButtonText: String = "Cancel",
                           confirmButtonFunction: (String) -> Unit) {
        // Create the alert builder
        val dialogBuilder = AlertDialog.Builder(this)

        // Add the custom view
        val li = LayoutInflater.from(this)
        val textFieldView = li.inflate(R.layout.alert_with_text_field, null)
        val textField = textFieldView.findViewById<EditText>(R.id.alertTextField)
        textField.hint = hint
        if (isPassword) { textField.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD }
        dialogBuilder.setView(textFieldView)

        // Set up the buttons
        dialogBuilder
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(confirmButtonText, DialogInterface.OnClickListener { dialog, _ ->
                confirmButtonFunction(textField.text.toString())
            })
            .setNegativeButton(cancelButtonText, DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })

        // Create the dialog box
        val alert = dialogBuilder.create()

        // Set the title of the alert
        alert.setTitle(title)

        // Show the alert
        alert.show()
    }
}
