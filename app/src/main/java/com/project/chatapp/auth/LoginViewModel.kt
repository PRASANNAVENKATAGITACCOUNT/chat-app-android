package com.project.chatapp.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class LoginViewModel: ViewModel() {


    fun launchCatching(block: suspend CoroutineScope.() -> Unit){
        viewModelScope.launch {
            CoroutineExceptionHandler { coroutineContext,throwable ->
                Log.d("Exception ", "launchCatching: ${throwable.message}")
            }
            block
        }
    }

}