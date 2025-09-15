package com.project.chatapp.presentation.common

import com.project.chatapp.model.User

sealed class Resource<T> (val data: T?=null, message: String?=null) {

    class Loading<T>(message: String?="Loading..."): Resource<T>(data = null,message)

    class Success<T>(data:T?=null): Resource<T>(data=data)

    class Error<T>(errorMessage: String) : Resource<T>(message = errorMessage)

}