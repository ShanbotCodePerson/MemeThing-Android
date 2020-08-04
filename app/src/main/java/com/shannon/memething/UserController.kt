package com.shannon.memething

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

object UserController {

    // Source of truth

    var currentUser: User? = null
    var usersFriends: ArrayList<User>? = null

    // Properties

    val db = Firebase.firestore

    // CRUD Methods

    // Create a new user
    fun createUser(email: String, screenName: String?, completion: (Boolean) -> Void) {
        // Create the new user
        var user = User(email, screenName)

        // Save the user object to the cloud and save the documentID for editing purposes
        TODO("save new user to cloud and get docID")
    }

    // Read (fetch) the current user
    fun fetchUser(email: String, completion: (Boolean) -> Unit) {
        db.collection(userRecordType)
            .whereEqualTo(emailKey, email)
            .get()
            .addOnSuccessListener { documents ->
                var document = documents.first()

                // Set up the user with the data fetched from the cloud
                var user = User(email, null)
                var success = user.setUpFromDictionary(document.data)

                // Save to the source of truth and return the success
                currentUser = user
                // to do - set up notification subscriptions
                completion(success)
            }
            .addOnFailureListener { exception ->
                // Print and return the error
                Log.i("ERROR", "Error in fetchUser in UserController: ${exception.toString()}")
                completion(false)
            }
    }

}