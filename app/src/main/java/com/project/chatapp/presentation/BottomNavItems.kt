package com.project.chatapp.presentation

import com.project.chatapp.R

sealed class BottomNavItems (val route: String, val iconRes: Int, val label: String){
    object ChatsScreen:BottomNavItems("chats", R.drawable.chats,"Chats")
    object UpdatesScreen:BottomNavItems("status", R.drawable.status_chat_icon,"Status")

    companion object{
        val values = listOf(ChatsScreen,UpdatesScreen)
    }
}