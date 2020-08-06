package com.shannon.memething

import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
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
    fun createUser(email: String, screenName: String?, completion: (Boolean) -> Unit) {
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
                // Unwrap the data
                var user = documents.first().toObject<User>()

                // Save to the source of truth and return the success
                currentUser = user
                // to do - set up notification subscriptions
                completion(true)
            }
            .addOnFailureListener { exception ->
                // Print and return the error
                Log.i("ERROR", "Error in fetchUser in UserController: ${exception.toString()}")
                completion(false)
            }
    }

    // Read (fetch) the current user's friends
    fun fetchUsersFriends(completion: (ArrayList<User>?) -> Unit) {
        if (currentUser == null) {
            completion(null)
            return
        }
        // Return an empty array if the user has no friends
        if (currentUser!!.friendIDs.count() == 0) {
            usersFriends = ArrayList<User>()
            completion(ArrayList<User>())
            return
        }

        // Fetch the data from the cloud
        db.collection(userRecordType)
            .whereIn(userRecordIDKey, currentUser!!.friendIDs)
            .get()
            .addOnSuccessListener {
                // Unwrap the data
                var users = it.documents.mapNotNull { it.toObject<User>() }
                users.sortedByDescending { it.points }
                users = ArrayList<User>(users)

                // Save to the source of truth and return the result
                usersFriends = users
                completion(users)
            }
            .addOnFailureListener {
                // Print and return the error
                Log.i("ERROR", "Error in fetchUser in UserController: ${it.toString()}")
                completion(null)
            }
    }

}