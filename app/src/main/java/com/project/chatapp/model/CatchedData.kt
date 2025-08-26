package com.project.chatapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_messages")
data class CachedMessage(
    @PrimaryKey val id: String,
    val conversationId: String,
    val senderId: String,
    val content: String,
    val messageType: String,
    val timestamp: Long,
    val deliveryStatus: String,
    val isSynced: Boolean = false
)

@Entity(tableName = "cached_conversations")
data class CachedConversation(
    @PrimaryKey val id: String,
    val participantsJson: String, // JSON string of participants list
    val type: String,
    val title: String,
    val lastMessageJson: String?, // JSON string of LastMessage
    val updatedAt: Long
)