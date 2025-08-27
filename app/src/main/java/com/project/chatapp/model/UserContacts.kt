package com.project.chatapp.model


// Individual Contact Model
data class Contact(
    val contactId: String = "",           // The other user's ID
    val contactName: String = "",         // Display name for this contact
    val addedAt: Long = System.currentTimeMillis(),
    val isBlocked: Boolean = false,
    val isFavorite: Boolean = false,
    val lastMessageTime: Long = 0L        // For sorting recent contacts
)

// User's Contacts Collection Model
data class UserContacts(
    val userId: String = "",
    val contactsCount: Int = 0            // For quick stats
)


