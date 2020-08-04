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
private val recordIDKey = "recordID"

class User(email: String, screenName: String?) {
    // Properties
    var email = email
    var screenName: String
    var profilePhotoURL = ""
    var photo: Image?
        get() {
            return null
        }
        set(value) {
            photo = value
        }
    var points = 0
    var blockedIDs = ArrayList<String>()
    var friendIDs = ArrayList<String>()
    var recordID: String

    // Initializer

    init {
        if (screenName.isNullOrEmpty()) {
            this.screenName = email.substring(0, email.indexOf("@"))
        } else {
            this.screenName = screenName
        }
        recordID = UUID.randomUUID().toString()
    }

    // Set up from dictionary

    fun setUpFromDictionary(dictionary: MutableMap<String, Any>): Boolean {
        // Get the values from the dictionary
        var email = dictionary.get(emailKey).toString()
        var screenName = dictionary.get(screenNameKey).toString()
        var profilePhotoURL = dictionary.get(profilePhotoURLKey).toString()
        var points = dictionary.get(pointsKey).toString().toInt()
        var blockedIDs = dictionary.get(blockedIDsKey) as ArrayList<String>
        var friendIDs = dictionary.get(friendIDsKey) as ArrayList<String>
        var recordID = dictionary.get(recordIDKey).toString()

        // Make sure the values are not null
        if (email.isNullOrEmpty() || screenName.isNullOrEmpty() || recordID.isNullOrEmpty()) { return false }

        // Save the values
        this.email = email
        this.screenName = screenName
        this.profilePhotoURL = profilePhotoURL
        this.points = points
        this.blockedIDs = blockedIDs
        this.friendIDs = friendIDs
        this.recordID = recordID
        return true
    }

    // Convert to Dictionary

    fun asDictionary(): HashMap<String, Any> {
        return hashMapOf(
            emailKey to email,
            screenNameKey to screenName,
            profilePhotoURLKey to profilePhotoURL,
            pointsKey to points,
            blockedIDsKey to blockedIDs,
            friendIDsKey to friendIDs,
            recordIDKey to recordID)
    }
}