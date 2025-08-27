package com.project.chatapp.presentation.common

import com.project.chatapp.model.User

sealed class Resource<T> (val data: T?, message: String) {

}