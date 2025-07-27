package com.project.chatapp.home

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.chatapp.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel : ViewModel() {
    private var _listOfContacts :MutableStateFlow<List<Contact>> = MutableStateFlow(mutableListOf<Contact>())
    val listOfContacts :StateFlow<List<Contact>> = _listOfContacts.asStateFlow()

    fun setListOfContacts(list:List<Contact>){
        _listOfContacts.value=list
    }
}