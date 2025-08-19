package com.project.chatapp.auth.services

import com.google.firebase.auth.FirebaseUser
import com.project.chatapp.model.User
import kotlinx.coroutines.flow.Flow

public interface AuthenticationService {

    val currentUser: Flow<FirebaseUser?>

    val currentUserId: String

    suspend fun signIn(email: String, password:String)

    suspend fun signUp(email:String, password: String)

    fun hasUser(): Boolean

    suspend fun signOut()

    suspend fun deleteAccount()


}