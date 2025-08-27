package com.project.chatapp.domain.firebase_repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.project.chatapp.domain.Constants
import com.project.chatapp.model.User

class FBUserRepository {
    
    val databaseRef = FirebaseDatabase.getInstance().reference
    val userRef = databaseRef.child(Constants.USERS)





    fun createOrUpdateUser(user: User){
        userRef
            .child(user.uid)
            .setValue(user)
            .addOnSuccessListener { result->
                Log.d("", "createOrUpdateUser: ")
            }
            .addOnFailureListener { error->
                Log.d("", "createOrUpdateUser: ")
            }
    }

    fun getUser(userId: String, onResult :(User?) -> Unit){
        userRef
            .child(userId).get()
            .addOnSuccessListener {
                val user = it.getValue(User::class.java)
                onResult(user)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun deleteUser(userId: String) {
        userRef
            .child(userId).removeValue()
    }



}