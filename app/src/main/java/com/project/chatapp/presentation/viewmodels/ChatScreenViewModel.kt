package com.project.chatapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.project.chatapp.domain.firebase_repository.FBConversationRepository
import com.project.chatapp.domain.firebase_repository.FBMessageRespository
import com.project.chatapp.domain.firebase_repository.FBUserRepository
import com.project.chatapp.model.Message
import com.project.chatapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow

class ChatScreenViewModel: ViewModel() {

    val fbConversationRepository = FBConversationRepository()
    val fbMessageRespository = FBMessageRespository()
    val fbUserRepository = FBUserRepository()
    val currentUser = MutableStateFlow<User?>(null)
    val connectedUser = MutableStateFlow<User?>(null)

    var currentConversationId  = MutableStateFlow<String?>(null)




    fun getAllMessages(conversationId: String, onResult:(List<Message>)-> Unit){
        fbMessageRespository.fetchMessages(conversationId){messages ->
            onResult(messages)
        }
    }


    fun getUser(currentUserUId: String, connectedUserId: String){
        fbUserRepository.getUser(currentUserUId){ user->
            currentUser.value=user
        }
        fbUserRepository.getUser(connectedUserId){user->
            connectedUser.value=user
        }
    }



    fun createConversation(currentUserUid : String,
                           connectedUserId : String,
                           getConversationResult: (String?)-> Unit) {
        fbConversationRepository.createConversationIfNotFound(currentUserUid,connectedUserId){
            getConversationResult(it)
            currentConversationId.value = it
        }
    }

    fun createMessage(message: Message) {
        Log.d("jvfd87", ":-> ${currentConversationId.value}  ")
        currentConversationId.value?.let {
            fbMessageRespository.createMessage(it,message)
        }
    }
}