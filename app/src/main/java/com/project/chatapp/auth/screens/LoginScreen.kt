package com.project.chatapp.auth.screens

import android.content.Intent
import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.project.chatapp.presentation.HomeScreenActivity


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel<LoginViewModel>()) {

    val intent = Intent(LocalContext.current, HomeScreenActivity::class.java)
    val context=LocalContext.current


    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (loginPageContainer) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
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
                var userEmail by remember {
                    mutableStateOf("")
                }
                var userPassword by remember {
                    mutableStateOf("")
                }
                InputFieldUI(
                    "Email",
                    value = userEmail,
                    onValueListener = {
                        userEmail = it
                    },
                    isPassword = false,
                )
                InputFieldUI(
                    "Password",
                    value = userPassword,
                    onValueListener = {
                        userPassword = it
                    },
                    isPassword = true,
                    passwordType = PASSWORD_TYPE.CURRENT_PASSWORD
                )


                Text(
                    text = "Forgot Password?",
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .align(Alignment.End)
                )
                Button(
                    onClick = {
                        loginViewModel.launchCatching {
                            try {
                                val authResult= loginViewModel.signInUser(userEmail, userPassword)
                                authResult.let {
                                    if(it.user!=null){
                                        context.startActivity(intent)
                                    }
                                }
                            }catch (e: FirebaseAuthException){
                                Log.d(LoginViewModel.TAG, "LoginScreen : $e")
                            }
                        }
                    },
                    modifier = Modifier
                        .width(300.dp)
                        .height(60.dp)
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)) {
                    Text(text = "Login")
                }
                Divider(
                    Modifier
                        .padding(top = 15.dp)
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
