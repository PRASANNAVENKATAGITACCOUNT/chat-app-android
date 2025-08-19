package com.project.chatapp.main_app.chats_screens

import android.os.Bundle
import androidx.activity.compose.setContent
import com.project.chatapp.BaseActivity
import com.project.chatapp.main_app.chats_screens.constants.CONTACT_SELECTED
import com.project.chatapp.model.Contact
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
