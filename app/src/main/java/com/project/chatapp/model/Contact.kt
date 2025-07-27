package com.project.chatapp.model

import android.net.Uri
import android.os.Parcelable
import java.io.Serializable
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime


data class Contact(
    var id:Long=0L,
    var name:String="",
    var phoneNumber:String="",
    val listOfMessages:MutableList<Message> = mutableListOf(),
    var contactPhotoUriString: String?=null
) : Serializable{

    fun getContactPhotoUri(): Uri? {
        return contactPhotoUriString?.let { Uri.parse(it) }
    }

    fun setContactPhotoUri(uri: Uri?) {
        contactPhotoUriString = uri?.toString()
    }
}


data class Message(var message:String="",
                   val sentOrReceiver:MessageSentOrReceived=MessageSentOrReceived.SENT,
                   var messageDateTime: LocalDateTime?=null)

enum class MessageSentOrReceived{
    SENT,RECEIVED
}