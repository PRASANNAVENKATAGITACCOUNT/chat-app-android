package com.project.chatapp.domain.firebase_repository

import com.google.firebase.database.FirebaseDatabase
import com.project.chatapp.domain.Constants
import com.project.chatapp.model.Message

class FBMessageRespository {

    val databaseRef = FirebaseDatabase.getInstance().reference
    val messagesRef = databaseRef.child(Constants.MESSAGES)

    fun fetchMessages(conversationId: String, callback: (List<Message>) -> Unit) {
        messagesRef.child(conversationId)
            .orderByChild("timestamp")
            .get()
            .addOnSuccessListener { snapshot ->
                val messages = snapshot.children.mapNotNull { it.getValue(Message::class.java) }
                callback(messages)
            }
    }



}