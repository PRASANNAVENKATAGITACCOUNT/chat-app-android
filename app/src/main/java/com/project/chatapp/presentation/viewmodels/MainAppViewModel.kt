package com.project.chatapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.project.chatapp.auth.services.impl.FirebaseAuthImpl
import com.project.chatapp.domain.firebase_repository.FBContactRepository
import com.project.chatapp.domain.firebase_repository.FBConversationRepository
import com.project.chatapp.domain.firebase_repository.FBMessageRespository
import com.project.chatapp.domain.firebase_repository.FBUserRepository
import com.project.chatapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainAppViewModel : ViewModel() {
    private var _listOfContacts : MutableStateFlow<List<User>> =
        MutableStateFlow(mutableListOf<User>())
    val listOfContacts : StateFlow<List<User>> = _listOfContacts.asStateFlow()

    val authenticationService = FirebaseAuthImpl()

    val fbUserRepo = FBUserRepository()
    val fbMessageRepo = FBMessageRespository()
    val fbConversationRepo = FBConversationRepository()
    val fbContactRepo = FBContactRepository()


    init {
        fbContactRepo.getUserConnections(authenticationService.currentUserId){ users->
            setListOfContacts(users)
        }
    }

    fun setListOfContacts(list:List<User>){
        _listOfContacts.value=list
    }
}