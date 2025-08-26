package com.project.chatapp.main_app.viewmodels

import androidx.lifecycle.ViewModel
import com.project.chatapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainAppViewModel : ViewModel() {
    private var _listOfContacts : MutableStateFlow<List<User>> =
        MutableStateFlow(mutableListOf<User>())
    val listOfContacts : StateFlow<List<User>> = _listOfContacts.asStateFlow()

    fun setListOfContacts(list:List<User>){
        _listOfContacts.value=list
    }
}