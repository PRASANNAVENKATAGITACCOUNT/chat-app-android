package com.project.chatapp.presentation.chats_screens

import android.os.Bundle
import androidx.activity.compose.setContent
import com.project.chatapp.BaseActivity
import com.project.chatapp.model.User
import com.project.chatapp.presentation.common.Constants
import com.project.chatapp.presentation.common.Constants.CONTACT_SELECTED
import com.project.chatapp.presentation.common.Constants.SELECTED_CONTACT_UID
import com.project.chatapp.ui.theme.ChatAppTheme

class ChatScreenActivity:BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                val selectedUserUID = intent?.getStringExtra(SELECTED_CONTACT_UID) ?:""
                val currentUserUID = intent?.getStringExtra(Constants.CURRENT_USER_UID)?: ""
                ChatScreen(currentUserUID,selectedUserUID)
            }
        }
    }

}
