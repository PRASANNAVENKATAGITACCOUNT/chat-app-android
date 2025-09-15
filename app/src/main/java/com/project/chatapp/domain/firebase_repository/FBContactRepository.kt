package com.project.chatapp.domain.firebase_repository

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.project.chatapp.domain.Constants
import com.project.chatapp.model.Contact
import com.project.chatapp.model.User


class FBContactRepository {

    val db = FirebaseDatabase.getInstance().reference
    val contactRef = db.child(Constants.CONTACTS)
    val userRef= db.child(Constants.USERS)


    fun getUserContacts(userId: String, callback: (List<Contact>) -> Unit) {
        val db = FirebaseDatabase.getInstance().reference
        contactRef.child(userId).get()
            .addOnSuccessListener { snapshot ->
                val contacts = snapshot.children.mapNotNull { it.getValue(Contact::class.java) }
                callback(contacts)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }


    fun getUserConnections(
        userId: String,
        getConnectedUser: (List<User>) -> Unit) {
        contactRef.child(userId).get()
            .addOnSuccessListener { snapshot ->
                val contactIds = snapshot.children.mapNotNull { it.key }
                val usersList = mutableListOf<User>()

                val tasks = contactIds.map { contactId ->
                    userRef.child(contactId).get()
                }
                Log.d("bbchf", "Success getUserConnections: $contactIds")

                Tasks.whenAllSuccess<DataSnapshot>(tasks)
                    .addOnSuccessListener { results ->
                        results.forEach { snap ->
                            snap.getValue(User::class.java)?.let { usersList.add(it) }
                        }
                        getConnectedUser(usersList)
                    }
                    .addOnFailureListener {e->
                        Log.d("bbchf", "getUser: ${e.message}")
                    }
            }
            .addOnFailureListener {e->
                Log.d("bbchf", "getUserConnections: ${e.message}")
                getConnectedUser(emptyList())
            }
    }

}