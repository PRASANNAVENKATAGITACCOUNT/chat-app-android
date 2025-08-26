package com.project.chatapp.model

data class Message(
    val id: String = "",
    val conversationId: String = "",
    val senderId: String = "",
    val content: String = "",
    val messageType: MessageType = MessageType.TEXT,
    val timestamp: Long = System.currentTimeMillis(),
    val deliveryStatus: DeliveryStatus = DeliveryStatus.SENT,
    val readBy: Map<String, Long> = emptyMap(), // userId -> read timestamp
    val editedAt: Long? = null,
    val replyToMessageId: String? = null,
    val mediaUrl: String? = null,
    val fileName: String? = null,
    val fileSize: Long? = null
)

data class LastMessage(
    val content: String = "",
    val senderId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val messageType: MessageType = MessageType.TEXT
)


enum class MessageType {
    TEXT,
    IMAGE,
    VIDEO,
    AUDIO,
    FILE,
    SYSTEM
}

enum class DeliveryStatus {
    SENT,
    DELIVERED,
    READ
}