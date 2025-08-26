package com.project.chatapp.auth.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.project.chatapp.auth.services.impl.FirebaseAuthImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {
    companion object{
        val TAG="LoginActivity "
    }

//    private val _user = MutableStateFlow(User())
//    val user = _user.asStateFlow()

    val authenticationService = FirebaseAuthImpl()



    fun launchCatching(block: suspend CoroutineScope.()-> Unit){
        viewModelScope.launch {
            CoroutineExceptionHandler {_, throwable->
                Log.d(TAG, "launchCatching: ${throwable.message}")
            }
            block()
        }
    }


    suspend fun signInUser(email: String, password: String): AuthResult {
        return withContext(Dispatchers.IO) {
            authenticationService.signIn(email, password)
        }
    }

    suspend fun signUpUser(email: String, password: String): AuthResult {
        return withContext(Dispatchers.IO) {
            authenticationService.signUp(email, password)
        }
    }



}