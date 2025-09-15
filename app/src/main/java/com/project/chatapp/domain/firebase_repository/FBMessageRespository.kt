package com.project.chatapp.domain.firebase_repository

import android.util.Log
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
                Log.d("vahjbzx", "Success ")
            }
            .addOnFailureListener {
                Log.d("vahjbzx", "fetchMessages: ${it.message}")
            }
    }

    fun observeMessages(){

    }


    fun createMessage(conversationId:String,message: Message){
        val messageId = messagesRef.child(conversationId).push().key
        messageId?.let {
            message.id=it
            messagesRef
                .child(conversationId)
                .child(messageId)
                .setValue(message)
                .addOnSuccessListener {
                    Log.d("bfdcb123", "Success")
                }
                .addOnFailureListener { e->
                    Log.d("bfdcb123", "Failure : ${e.message}")
                }
        }
    }



}