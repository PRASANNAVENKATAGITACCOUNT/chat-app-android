package com.project.chatapp.auth.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.chatapp.R


@Composable
fun AuthUI(title:String, iconRes:Int, onClick:()->Unit) {
    Button(onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White),
        border = BorderStroke(width = 2.dp, color = Color.Gray),
        modifier = Modifier
            .width(140.dp)
            .height(45.dp)) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Image(painter = painterResource(id = iconRes),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(5.dp))
            Text(text = title)
        }
    }

}




@Composable
fun InputFieldUI(title:String, value : String ,isPassword:Boolean=false, onValueListener: (String)->Unit) {

    var passwordFieldHolder by remember {
        mutableStateOf(PasswordFieldHolder())
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = title,
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(top = 8.dp, start = 10.dp)
                .height(25.dp)
                .align(Alignment.Start)
        )
        TextField(
            value = value,
            onValueChange = { onValueListener(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .clip(RoundedCornerShape(58f))
                ,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            visualTransformation = passwordFieldHolder.visualType,
            trailingIcon = {
                if(isPassword){
                    IconButton(onClick = {
                        if(passwordFieldHolder.visualType == VisualTransformation.None){
                            passwordFieldHolder = PasswordFieldHolder(
                                visualType = PasswordVisualTransformation(),
                                icon =R.drawable.eye_close_lock
                            )
                        }else{
                            passwordFieldHolder = PasswordFieldHolder(
                                visualType = VisualTransformation.None,
                                icon =R.drawable.eye_open
                            )
                        }
                    }) {
                        Icon(
                            painterResource(id = passwordFieldHolder.icon),
                            contentDescription ="",
                            modifier = Modifier
                                .height(21.dp)
                                .width(21.dp)
                        )
                    }
                }
            }

        )
    }
}

data class PasswordFieldHolder(
    var visualType : VisualTransformation = VisualTransformation.None,
    var icon : Int = R.drawable.eye_open
    )