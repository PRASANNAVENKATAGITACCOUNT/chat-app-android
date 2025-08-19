package com.project.chatapp.auth.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.chatapp.R


@Preview
@Composable
fun RegisterScreen() {

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

                InputFieldUI(title = "Confirm Email", value = userData.conformEmail) {
                    userData = UserForm(
                        email = userData.email,
                        password = userData.password,
                        conformEmail = it,
                        confirmPassword = userData.confirmPassword)
                }

                InputFieldUI(title = "Password",isPassword = true, value = userData.password){
                    userData = UserForm(
                        email = userData.email,
                        conformEmail = userData.conformEmail,
                        confirmPassword = userData.confirmPassword,
                        password = it)
                }

                InputFieldUI(title = "Confirm Password",isPassword = true, value= userData.confirmPassword) {
                    userData = UserForm(
                        email = userData.email,
                        conformEmail = userData.conformEmail,
                        confirmPassword = it,
                        password = userData.password)
                }

                Text(
                    text = "Forgot Password?",
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .align(Alignment.End)
                )
                Button(onClick = { },
                    modifier = Modifier
                        .width(300.dp)
                        .height(60.dp)
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)) {
                    Text(text = "Login")
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
)

