package com.project.chatapp.main_app

import androidx.lifecycle.ViewModel
import com.project.chatapp.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainAppViewModel : ViewModel() {
    private var _listOfContacts :MutableStateFlow<List<Contact>> = MutableStateFlow(mutableListOf<Contact>())
    val listOfContacts :StateFlow<List<Contact>> = _listOfContacts.asStateFlow()

    fun setListOfContacts(list:List<Contact>){
        _listOfContacts.value=list
    }
}