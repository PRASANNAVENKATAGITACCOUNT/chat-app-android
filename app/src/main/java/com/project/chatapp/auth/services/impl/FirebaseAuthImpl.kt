package com.project.chatapp.auth.services.impl

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.project.chatapp.auth.services.AuthenticationService
import com.project.chatapp.domain.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

object FirebaseAuthImpl: AuthenticationService {

    override val currentUser: Flow<FirebaseUser?>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener{ auth->
                trySend(auth.currentUser)
            }
            Firebase.auth.addAuthStateListener(listener)

            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }


    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override suspend fun signIn(email: String, password: String) : AuthResult {
        val authResult = Firebase.auth.signInWithEmailAndPassword(email,password).await()
        return authResult
    }

    override suspend fun signUp(email: String, password: String) : AuthResult? {
        return try{
            Firebase.auth.createUserWithEmailAndPassword(email,password).await()
        }catch (e: FirebaseAuthUserCollisionException){
            Log.d("njff", " Already Email exists")
            null
        }catch (excep: Exception){
            Log.d("njff", " Some error occurred")
            null
        }
    }

    override fun hasUser(onResult:(Boolean)-> Unit){
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            val user = auth.currentUser
            if (user != null) {
              onResult(true)
            } else {
              onResult(false)
            }
        }
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }


}