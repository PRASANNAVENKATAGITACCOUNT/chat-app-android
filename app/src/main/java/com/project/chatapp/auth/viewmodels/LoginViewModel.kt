package com.project.chatapp.auth.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.FirebaseDatabase
import com.project.chatapp.auth.services.impl.FirebaseAuthImpl
import com.project.chatapp.domain.Constants
import com.project.chatapp.domain.firebase_repository.FBUserRepository
import com.project.chatapp.model.User
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {
    companion object{
        val TAG="LoginActivity "
    }

    val fbUserRepo = FBUserRepository()

    val authenticationService = FirebaseAuthImpl



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

    suspend fun signUpUser(email: String, password: String) : AuthResult? {

        return withContext(Dispatchers.IO) {
            val authResult=authenticationService.signUp(email, password)
            authResult
        }
    }

    fun saveUserInDB(user: User, onResult :(String)->Unit){
        fbUserRepo.createOrUpdateUser(user){ result->
            onResult(result)
            Log.d("vgnbh", result)
        }
    }



}