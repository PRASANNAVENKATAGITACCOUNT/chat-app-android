package com.project.chatapp.main_app.chats_screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import coil.compose.AsyncImage
import com.project.chatapp.R
import com.project.chatapp.model.Contact
import com.project.chatapp.model.Message
import com.project.chatapp.model.MessageSentOrReceived


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(contact:Contact=Contact(), createMessage:(Message)->Unit={}) {

    val listOfMessages = remember {
        mutableStateListOf<Message>(
            Message(sentOrReceiver = MessageSentOrReceived.RECEIVED, message = "Hello World"),
            Message(message = "Hello World 2")
        )
    }

    Scaffold (
        topBar = {
            ChatTopAppBar(contact)
                 },
        bottomBar = {BottomChatAppBar{chatText->
            val message = Message(message = chatText)
            listOfMessages.add(message)
            createMessage(message)
        } },
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues())
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                items(listOfMessages){message->
                    Chat(message)
                }
            }
        }
    }
}



@Composable
fun Chat(message: Message=Message(message = "Hello ")) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(2.dp),
        horizontalAlignment = if(message.sentOrReceiver==MessageSentOrReceived.SENT){
            Alignment.End
        }else{
            Alignment.Start
        }
        )
     {
        Card(
            modifier = Modifier,
            shape = if(message.sentOrReceiver==MessageSentOrReceived.SENT){
                RoundedCornerShape(bottomEnd = 0f, bottomStart = 15f, topStart = 15f, topEnd = 15f)
            }else{
                RoundedCornerShape(bottomEnd = 15f, bottomStart = 0f, topStart = 15f, topEnd = 15f)
            }
        ) {
            Text(
                text = message.message,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .wrapContentWidth()
                    .background(
                        color = if (message.sentOrReceiver == MessageSentOrReceived.SENT) {
                            Color.Blue
                        } else {
                            Color.Green
                        }
                    )
                    .padding(12.dp)
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(contact: Contact= Contact(name = "dummy")) {
    Surface (shadowElevation = 8.dp){
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AsyncImage(
                        model = contact.getContactPhotoUri()?:Uri.EMPTY,
                        contentDescription = "",
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp)
                            .clip(CircleShape))
                    Column (
                        modifier = Modifier.padding(start = 5.dp)
                    ){
                        Text(text = if (contact.name.isEmpty()) contact.phoneNumber else contact.name, style = TextStyle(fontSize = 15.sp))
                        Text(text = "Last Screen Todayat 8:23 pm", style = TextStyle(fontSize = 8.sp))
                    }
                    IconButton(onClick = { }) {
                        Icon(painter = painterResource(id = R.drawable.vedio_icon), contentDescription = "")
                    }
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Call, contentDescription = "")
                    }
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "")
                    }
                }
            },
            navigationIcon = { IconButton(onClick = {  }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
            }}
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomChatAppBar(onChatComplete:(String)->Unit) {
    var chatText by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextField(
            value = chatText,
            onValueChange =
            { text ->
                chatText = text
            },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.sticker_emoji_vector),
                        contentDescription = ""
                    )
                }
            },
            trailingIcon = {
                Row {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.attach_file_icon),
                            contentDescription = ""
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(RoundedCornerShape(55f))
        )
        if (chatText.isEmpty()) {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.microphone_xml),
                    contentDescription = "",
                    modifier = Modifier
                        .background(color = Color.Green)
                        .clip(RoundedCornerShape(55f))
                        .padding(11.dp)
                )
            }
        }else {
            IconButton(onClick = {
                onChatComplete(chatText)
                chatText = ""
            }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    modifier = Modifier
                        .background(color = Color.Green)
                        .clip(RoundedCornerShape(55f))
                        .padding(11.dp)
                )
            }
        }
    }
}


