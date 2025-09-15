package com.project.chatapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.chatapp.auth.services.impl.FirebaseAuthImpl
import com.project.chatapp.auth.viewmodels.LoginViewModel.Companion.TAG
import com.project.chatapp.domain.firebase_repository.FBContactRepository
import com.project.chatapp.domain.firebase_repository.FBConversationRepository
import com.project.chatapp.domain.firebase_repository.FBMessageRespository
import com.project.chatapp.domain.firebase_repository.FBUserRepository
import com.project.chatapp.model.User
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainAppViewModel : ViewModel() {
    private var _listOfContacts : MutableStateFlow<List<User>> =
        MutableStateFlow(mutableListOf<User>())
    val listOfConnectedUser : StateFlow<List<User>> = _listOfContacts.asStateFlow()

    val authenticationService = FirebaseAuthImpl

    val fbUserRepo = FBUserRepository()
    val fbMessageRepo = FBMessageRespository()
    val fbConversationRepo = FBConversationRepository()
    val fbContactRepo = FBContactRepository()



    fun launchCatching(block: suspend CoroutineScope.()-> Unit){
        viewModelScope.launch {
            CoroutineExceptionHandler {_, throwable->
                Log.d(TAG, "launchCatching: ${throwable.message}")
            }
            block()
        }
    }


    init {
        if(authenticationService.currentUserId.isNotEmpty()){
            fbContactRepo.getUserConnections(authenticationService.currentUserId){ users->
                setListOfContacts(users)
            }
        }

    }

    fun addContactToUser(currentUserId: String,
                         friendEmailId: String, onResult:()-> Unit){
        fbUserRepo.findAndAddContact(currentUserId,friendEmailId){
            onResult()
        }
    }




    fun setListOfContacts(list:List<User>){
        _listOfContacts.value=list
    }


}