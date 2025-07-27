package com.project.chatapp.chats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.project.chatapp.BaseActivity
import com.project.chatapp.chats.constants.CONTACT_SELECTED
import com.project.chatapp.model.Contact
import com.project.chatapp.model.Message
import com.project.chatapp.model.MessageSentOrReceived
import com.project.chatapp.ui.theme.ChatAppTheme

class ChatScreenActivity:BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                var contact=intent?.getSerializableExtra(CONTACT_SELECTED) as? Contact
                contact = contact?:Contact()
                ChatScreen(contact){new_message ->
                   
                }
            }
        }
    }

}
