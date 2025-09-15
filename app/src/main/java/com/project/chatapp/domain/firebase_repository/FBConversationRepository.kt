package com.project.chatapp.domain.firebase_repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.project.chatapp.domain.Constants
import com.project.chatapp.model.Conversation
import com.project.chatapp.model.ConversationType
import com.project.chatapp.model.LastMessage
import com.project.chatapp.model.User

class FBConversationRepository {

    val databaseRef = FirebaseDatabase.getInstance().reference
    val conversationRef = databaseRef.child(Constants.CONVERSATION)

    fun updateLastMessage(conversationId: String, lastMessage: LastMessage) {
        conversationRef.child(conversationId).child("lastMessage").setValue(lastMessage)
    }

    fun hasConversation(currentUid: String, connectedUid: String, onResult: (String?) -> Unit) {
        conversationRef
            .get()
            .addOnSuccessListener { snapshot ->
                var convId: String? = null
                for (snap in snapshot.children) {
                    val conversation = snap.getValue(Conversation::class.java)
                    if (conversation != null) {
                        // checking both users are participants
                        if (conversation.participants.contains(currentUid) &&
                            conversation.participants.contains(connectedUid)) {
                            convId = conversation.id
                            break
                        }
                    }
                }
                onResult(convId)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun createConversationIfNotFound(currentUserUid : String,
                                     connectedUserId : String,
                                     getConversationResult: (String?)-> Unit) {
        hasConversation(currentUserUid,connectedUserId){ conversationId ->
            if(conversationId==null){
                val convId = conversationRef.push().key ?: return@hasConversation
                val conversation = Conversation(
                    id=convId,
                    participants = listOf(currentUserUid, connectedUserId)
                )

                try {
                    conversationRef
                        .child(convId)
                        .setValue(conversation)
                        .addOnSuccessListener {
                            Log.d("bfjd", " Success saving conversation ")
                        }
                        .addOnFailureListener {
                            Log.d("bfjd", "$it ")
                        }
                }catch (e: Exception){
                    Log.d("gdhj", "${e.message} ")
                }
            }else{
                getConversationResult(conversationId)
            }
        }
    }



}