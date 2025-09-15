package com.project.chatapp.model

data class Message(
    var id: String = "",
    val senderId: String = "",
    val content: String = "",
    val messageType: String = MessageType.TEXT.toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val deliveryStatus: DeliveryStatus = DeliveryStatus.SENT,
    val editedAt: Long? = null,
    val replyToMessageId: String? = null,
    val mediaUrl: String? = null,
    val fileName: String? = null,
    val fileSize: Long? = null
)

data class MessageRead(
    val userId: String = "",
    val readAt: Long = System.currentTimeMillis()
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