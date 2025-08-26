package com.project.chatapp.main_app.chats_screens

import android.os.Bundle
import androidx.activity.compose.setContent
import com.project.chatapp.BaseActivity
import com.project.chatapp.main_app.chats_screens.constants.CONTACT_SELECTED
import com.project.chatapp.model.User
import com.project.chatapp.ui.theme.ChatAppTheme

class ChatScreenActivity:BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                var user=intent?.getSerializableExtra(CONTACT_SELECTED) as? User
                user = user?:User()
                ChatScreen(user){ new_message ->
                   
                }
            }
        }
    }

}
