package com.project.chatapp.domain.firebase_repository

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.chatapp.domain.Constants
import com.project.chatapp.model.Contact
import com.project.chatapp.model.Conversation
import com.project.chatapp.model.User

class FBUserRepository {
    
    val databaseRef = FirebaseDatabase.getInstance().reference
    val userRef = databaseRef.child(Constants.USERS)
    val contactRef = databaseRef.child(Constants.CONTACTS)


    fun createOrUpdateUser(user: User, onResult:(String)-> Unit){

        userRef
            .child(user.uid)
            .setValue(user)
            .addOnSuccessListener { result->
                onResult("createOrUpdateUser: Success")
            }
            .addOnFailureListener { error->
                onResult("createOrUpdateUser: Failed ${error.message}")
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

    fun findAndAddContact(currentUserId: String,
                       friendEmailId: String,
                       onResult:()->Unit){
        databaseRef.child(Constants.USERS)
            .orderByChild("email")
            .equalTo(friendEmailId)
            .get()
            .addOnSuccessListener { snapshot ->
                val friend = snapshot.children.first().getValue(User::class.java)
                friend?.let { fri->
                    val contact = Contact(
                        contactId = fri.uid,
                        contactName = fri.email
                    )
                    contactRef
                        .child(currentUserId)
                        .child(fri.uid)
                        .setValue(contact)
                        .addOnSuccessListener {
                            Log.d("fjcfw1", "findAndAddContact: Success")
                        }
                        .addOnFailureListener { e ->
                            Log.e("fjcfw2", "Error saving contact: ${e.message}")
                        }
                    Log.d("fjcfw3", "findAndAddContact: Success")
                }

            }.addOnFailureListener { e->
                Log.d("fjcfw4", "findAndAddContact: ${e.message} ")
            }

    }

    fun deleteUser(userId: String) {
        userRef.child(userId).removeValue()
    }



}