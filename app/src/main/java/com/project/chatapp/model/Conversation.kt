package com.project.chatapp.model

data class Conversation(
    val id: String = "",
    val participants: List<String> = emptyList(), // List of user IDs
    val type: ConversationType = ConversationType.DIRECT,
    val title: String = "", // For group chats
    val lastMessage: LastMessage? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)


enum class ConversationType {
    DIRECT,
    GROUP
}