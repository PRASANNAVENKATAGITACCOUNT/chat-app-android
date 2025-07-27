package com.project.chatapp.auth.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.chatapp.R
import com.project.chatapp.ui.theme.DARK_GREEN
import com.project.chatapp.ui.theme.LIGHT_BLUE


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreen() {

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
                Text(
                    text = "Email",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 8.dp, start = 5.dp)
                )
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(58f)),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )

                Text(
                    text = "Password ",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(start = 5.dp)
                )
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(58f)),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),

                    )

                Text(
                    text = "Forgot Password?",
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .align(Alignment.End)
                )
                Button(onClick = { },
                    modifier = Modifier
                        .width(300.dp)
                        .height(70.dp)
                        .padding(top = 15.dp)
                        .align(Alignment.CenterHorizontally)) {
                    Text(text = "Login")
                }
                Divider(
                    Modifier
                        .padding(top = 15.dp)
                        .height(2.dp), color = Color.Gray)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White),
                        border = BorderStroke(width = 2.dp, color = Color.Gray),
                        modifier = Modifier.width(120.dp).height(45.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(painter = painterResource(id = R.drawable.google_icon),
                                contentDescription = "", modifier = Modifier.size(25.dp).padding(5.dp))
                            Text(text = "Google")
                        }

                    }
                    Button(onClick = { },
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White),
                        border = BorderStroke(width = 2.dp, color = Color.Gray),
                        modifier = Modifier.width(140.dp).height(45.dp)) {
                        Row (
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Image(painter = painterResource(id = R.drawable.facebook_icon),
                                contentDescription = "",
                                modifier = Modifier.size(25.dp).padding(5.dp))
                            Text(text = "Facebook")
                        }
                    }
                }


            }



        }








    }

}