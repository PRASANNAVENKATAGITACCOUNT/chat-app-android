package com.project.chatapp.auth.screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuthException
import com.project.chatapp.R
import com.project.chatapp.auth.viewmodels.LoginViewModel
import com.project.chatapp.model.User
import com.project.chatapp.presentation.HomeScreenActivity


@Preview
@Composable
fun RegisterScreen(loginViewModel: LoginViewModel = viewModel<LoginViewModel>()) {

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (loginPageContainer) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .constrainAs(loginPageContainer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center
            ) {
                val intent = Intent(LocalContext.current, HomeScreenActivity::class.java)
                val context=LocalContext.current

                var userData by remember {
                    mutableStateOf(UserForm())
                }

                InputFieldUI(title = "Email", value = userData.email) {
                    userData = UserForm(
                        email = it,
                        password = userData.password,
                        conformEmail = userData.conformEmail,
                        confirmPassword = userData.confirmPassword)
                }


                InputFieldUI(title = "Password",isPassword = true, value = userData.password, passwordType = PASSWORD_TYPE.NEW_PASSWORD){
                    userData = UserForm(
                        email = userData.email,
                        conformEmail = userData.conformEmail,
                        confirmPassword = userData.confirmPassword,
                        password = it)
                }

                InputFieldUI(title = "Confirm Password",isPassword = true, value= userData.confirmPassword, passwordType = PASSWORD_TYPE.CONFIRM_PASSWORD) {
                    userData = UserForm(
                        email = userData.email,
                        conformEmail = userData.conformEmail,
                        confirmPassword = it,
                        password = userData.password)
                }

                Button(onClick = {
                    if(userData.hasValidCredentials()){
                        loginViewModel.launchCatching {
                            try {
                                val authResult = loginViewModel.signUpUser(
                                    email = userData.email,
                                    password = userData.password
                                )
                                authResult?.let {
                                    if(it.user!=null){
                                        it.user?.let {currentUser->
                                            val user = User(
                                                uid=currentUser.uid,
                                                email = currentUser.email?:"",
                                            )
                                            loginViewModel.saveUserInDB(user){ result->
                                                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        context.startActivity(intent)
                                        Log.d(LoginViewModel.TAG, "RegisterScreen: User created and authenticated")
                                    }else{
                                        Log.d(LoginViewModel.TAG, "RegisterScreen: User not created")
                                    }
                                }

                            }catch (e: FirebaseAuthException){
                                Log.d(LoginViewModel.TAG, "RegisterScreen: ${e.message}")
                            }
                        }
                    }

                },
                    modifier = Modifier
                        .width(300.dp)
                        .height(60.dp)
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)) {
                    Text(text = "Register")
                }
                Divider(
                    Modifier
                        .padding(top = 8.dp)
                        .height(2.dp), color = Color.Gray)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {

                    AuthUI(title = "Google", iconRes = R.drawable.google_icon){

                    }
                    AuthUI(title = "Facebook", iconRes = R.drawable.facebook_icon){

                    }
                }

            }



        }

    }
}




data class UserForm(
    var email : String="",
    var password : String="",
    var conformEmail: String="",
    var confirmPassword: String=""
){
    fun hasValidCredentials() : Boolean{
        if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
            if(password == confirmPassword) return true
        }

        return false
    }
}

