package com.project.chatapp.domain.firebase_repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.project.chatapp.domain.Constants
import com.project.chatapp.model.Conversation
import com.project.chatapp.model.ConversationType
import com.project.chatapp.model.LastMessage

class FBConversationRepository {

    val databaseRef = FirebaseDatabase.getInstance().reference
    val conversationRef = databaseRef.child(Constants.CONVERSATION)

    fun createConversation(conversation: Conversation) {
        val convId = conversationRef.push().key ?: return
        conversationRef.child(convId).setValue(conversation.copy(id = convId))
    }

    fun updateLastMessage(conversationId: String, lastMessage: LastMessage) {
        conversationRef.child(conversationId).child("lastMessage").setValue(lastMessage)
    }

    fun getConversation(
        currentUserId: String,
        otherUserId: String,
        onResult:(Conversation)-> Unit){
        conversationRef
            .orderByChild("participants/$currentUserId")
            .equalTo(true)
            .get().addOnSuccessListener { dataSnapshot: DataSnapshot ->
            run outer@{
                var foundConversation: Conversation?=null
                dataSnapshot.children.forEach { child ->
                    val conversation = child.getValue(Conversation::class.java)
                    if (conversation?.participants?.containsKey(otherUserId) == true) {
                        foundConversation = conversation
                        onResult(foundConversation)
                        return@outer
                    }
                }
                if(foundConversation==null){
                    val convId = databaseRef.push().key ?: return@addOnSuccessListener
                    val newConv = Conversation(
                        id = convId,
                        participants = mapOf(
                            currentUserId to true,
                            otherUserId to true
                        ),
                        type = ConversationType.DIRECT,
                        createdAt = System.currentTimeMillis()
                    )
                    conversationRef.child(convId).setValue(newConv)
                    onResult(newConv)
                }
            }

        }.addOnFailureListener{ exception : Exception ->

        }
    }


}