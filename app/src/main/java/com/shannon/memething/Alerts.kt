package com.shannon.memething

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater

object Alerts {

    // A basic alert with a message and a dismiss button
    fun showBasicAlert(title: String, message: String, buttonText: String = "Dismiss", context: Context) {
        // Create the alert builder
        val dialogBuilder = AlertDialog.Builder(context)

        // Set up the title, message, and buttons
        dialogBuilder
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setNegativeButton(buttonText, DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })

        // Create the dialog box
        val alert = dialogBuilder.create()

        // Show the alert
        alert.show()
    }

    // A choice alert with yes and no buttons
    fun showChoiceAlert(title: String, message: String, yesButtonFunction: (Void) -> Unit,
                        yesButtonText: String = "Confirm", noButtonText: String = "No", context: Context) {
        // Create the alert builder
        val dialogBuilder = AlertDialog.Builder(context)

        // Set up the title, message, and buttons
        dialogBuilder
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(yesButtonText, DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
                yesButtonFunction
            })
            .setNegativeButton(noButtonText, DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })

        // Create the dialog box
        val alert = dialogBuilder.create()

        // Show the alert
        alert.show()
    }

    // An alert to show an error
    fun showErrorAlert() {

    }
}