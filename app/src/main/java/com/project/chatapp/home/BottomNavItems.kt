package com.project.chatapp.home

import com.project.chatapp.R

sealed class BottomNavItems (val route: String, val iconRes: Int, val label: String){
    object ChatsScreen:BottomNavItems("chats", R.drawable.chats,"Chats")
    object UpdatesScreen:BottomNavItems("updates", R.drawable.status_chat_icon,"Updates")
    object CommunitiesScreen:BottomNavItems("updates", R.drawable.communities_icon,"Communities")
    object CallsScreen:BottomNavItems("calls", R.drawable.call_icon,"Calls")

    companion object{
        val values = listOf(ChatsScreen,UpdatesScreen,CommunitiesScreen,CallsScreen)
    }
}