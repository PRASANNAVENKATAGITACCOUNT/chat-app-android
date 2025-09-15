package com.project.chatapp.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.project.chatapp.auth.services.impl.FirebaseAuthImpl
import com.project.chatapp.domain.firebase_repository.FBUserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {

    private var _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    val authenticationService = FirebaseAuthImpl

    val fbUserRepo = FBUserRepository()

    init {
        viewModelScope.launch {
            delay(300)
            _isLoading.value = false
        }
    }

    fun isUserPresent(onResult:(Boolean)-> Unit) {
        authenticationService.hasUser {
           _isLoading.value = false
            onResult(it)
        }
    }



}