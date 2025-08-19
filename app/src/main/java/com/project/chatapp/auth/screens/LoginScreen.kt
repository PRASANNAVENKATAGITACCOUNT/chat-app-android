package com.project.chatapp.auth.screens

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.chatapp.R
import com.project.chatapp.main_app.HomeScreenActivity
import com.project.chatapp.ui.theme.DARK_GREEN
import com.project.chatapp.ui.theme.LIGHT_BLUE


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreen() {

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
                var email by remember {
                    mutableStateOf("")
                }

                var password by remember {
                    mutableStateOf("")
                }

                InputFieldUI(title = "Email", value = email) {
                    email=it
                }

                InputFieldUI(title = "Password", isPassword = true, value = password) {
                    password=it
                }

                Text(
                    text = "Forgot Password?",
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .align(Alignment.End)
                )
                Button(
                    onClick = {
                        context.startActivity(intent)
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
