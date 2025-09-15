package com.project.chatapp.auth.services

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

public interface AuthenticationService {

    val currentUser: Flow<FirebaseUser?>

    val currentUserId: String

    suspend fun signIn(email: String, password:String) : AuthResult

    suspend fun signUp(email:String, password: String)  : AuthResult?

    fun hasUser(onResult:(Boolean)->Unit)

    suspend fun signOut()

    suspend fun deleteAccount()


}