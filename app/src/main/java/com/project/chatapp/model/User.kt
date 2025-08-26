package com.project.chatapp.model

import android.net.Uri
import java.io.Serializable


data class User(
    val uid: String="",
    var username:String="",
    val email: String="",
    var password:String="",
    val isOnline: Boolean=false,
    var lastSeen: Long = System.currentTimeMillis(),
    var createdAt: Long = System.currentTimeMillis(),
    var fcmToken: String = "",
    var contactId: Long=0L,
    var phoneNumber:String="",
    var contactPhotoUriString: String?=null
) : Serializable{

    fun getContactPhotoUri(): Uri? {
        return contactPhotoUriString?.let { Uri.parse(it) }
    }

    fun setContactPhotoUri(uri: Uri?) {
        contactPhotoUriString = uri?.toString()
    }
}

data class UserPresence(
    val userId: String = "",
    val isOnline: Boolean = false,
    val lastSeen: Long = System.currentTimeMillis(),
    val isTyping: Boolean = false,
    val typingInConversation: String? = null
)
