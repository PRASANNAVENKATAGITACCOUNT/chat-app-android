package com.project.chatapp.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.chatapp.auth.services.impl.FirebaseAuthImpl
import com.project.chatapp.domain.firebase_repository.FBUserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {

    private val mutableStateFlow = MutableStateFlow(true)
    val isLoading = mutableStateFlow.asStateFlow()

    val authenticationService = FirebaseAuthImpl()

    val fbUserRepo = FBUserRepository()

    init {
        viewModelScope.launch {
            delay(300)
            mutableStateFlow.value = false
        }
    }

    fun isUserPresent() : Boolean = authenticationService.hasUser()



}