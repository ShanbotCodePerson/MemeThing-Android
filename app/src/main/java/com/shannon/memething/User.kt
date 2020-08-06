package com.shannon.memething

import android.media.Image
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

// String constants
val userRecordType = "User"
val emailKey = "email"
private val screenNameKey = "screenName"
private val profilePhotoURLKey = "profilePhotoURL"
private val pointsKey = "points"
private val blockedIDsKey = "blockedIDs"
private val friendIDsKey = "friendIDs"
val userRecordIDKey = "recordID"

class User {
    // Properties
    lateinit var email: String
    lateinit var screenName: String
    var profilePhotoURL = ""
    var photo: Image? = null
    var points = 0
    var blockedIDs = ArrayList<String>()
    var friendIDs = ArrayList<String>()
    lateinit var recordID: String

    // Initializer

    constructor(email: String, screenName: String?, recordID: String? = null) {
        this.email = email

        if (screenName.isNullOrEmpty()) { this.screenName = email.substring(0, email.indexOf("@")) }
        else { this.screenName = screenName }

        if (recordID.isNullOrEmpty()) { this.recordID = UUID.randomUUID().toString() }
        else { this.recordID = recordID }
    }

//    // to do - make this constructor failable
//    constructor(dictionary: MutableMap<String, Any>) {
//
//    }

    constructor()

    // Set up from dictionary

//    fun setUpFromDictionary(dictionary: MutableMap<String, Any>): Boolean {
//        // Get the values from the dictionary
//        var email = dictionary.get(emailKey).toString()
//        var screenName = dictionary.get(screenNameKey).toString()
//        var profilePhotoURL = dictionary.get(profilePhotoURLKey).toString()
//        var points = dictionary.get(pointsKey).toString().toInt()
//        var blockedIDs = dictionary.get(blockedIDsKey) as ArrayList<String>
//        var friendIDs = dictionary.get(friendIDsKey) as ArrayList<String>
//        var recordID = dictionary.get(userRecordIDKey).toString()
//
//        // Make sure the values are not null
//        if (email.isNullOrEmpty() || screenName.isNullOrEmpty() || recordID.isNullOrEmpty()) { return false }
//
//        // Save the values
//        this.email = email
//        this.screenName = screenName
//        this.profilePhotoURL = profilePhotoURL
//        this.points = points
//        this.blockedIDs = blockedIDs
//        this.friendIDs = friendIDs
//        this.recordID = recordID
//        return true
//    }

    // Convert to Dictionary

    fun asDictionary(): HashMap<String, Any> {
        return hashMapOf(
            emailKey to email,
            screenNameKey to screenName,
            profilePhotoURLKey to profilePhotoURL,
            pointsKey to points,
            blockedIDsKey to blockedIDs,
            friendIDsKey to friendIDs,
            userRecordIDKey to recordID)
    }
}