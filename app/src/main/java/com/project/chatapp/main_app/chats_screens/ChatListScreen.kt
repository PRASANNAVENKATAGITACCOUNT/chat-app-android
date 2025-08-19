package com.project.chatapp.main_app.chats_screens


import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.project.chatapp.main_app.chats_screens.constants.CONTACT_SELECTED
import com.project.chatapp.model.Contact
import com.project.chatapp.ui.theme.DARK_GREEN
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(contacts:StateFlow<List<Contact>>) {
    val listOfContacts by contacts.collectAsState()
    val context = LocalContext.current
    Log.d("nfjnwf","$listOfContacts")


    var queryText by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
    ) {
        if(queryText.isEmpty()){
            ChatsScreenTopBar()
        }

        TextField(
            value = queryText,
            onValueChange = { queryText = it },
            placeholder = { Text("Search...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                if (queryText.isNotEmpty()) {
                    IconButton(onClick = {
                        queryText = ""
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            }
        )

        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(listOfContacts) { contact ->
                ChatContact(contact) { contact ->
                    val intent = Intent(context, ChatScreenActivity::class.java)
                    intent.putExtra(CONTACT_SELECTED, contact)
                    context.startActivity(intent)
                }
            }
        }

    }
}

@Preview
@Composable
fun ChatContact(contact: Contact= Contact(name = "dummy"),onClickContact:(Contact)->Unit={}) {
    Row (
        Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(10.dp)
            .clickable {
                onClickContact(contact)
            }
    ) {
         when(contact.getContactPhotoUri()){
             null->Image(
                 imageVector = Icons.Filled.AccountCircle,
                 contentDescription = "",modifier = Modifier
                     .clip(CircleShape)
                     .width(45.dp)
                     .height(45.dp))
             is Uri->{
                 AsyncImage(
                     model = contact.getContactPhotoUri(),
                     contentDescription = "",
                     modifier = Modifier
                         .clip(CircleShape)
                         .width(45.dp)
                         .height(45.dp)
                 )
             }
         }
        Column(
            Modifier.fillMaxSize()
        ) {
            val userData =
                if(contact.name.isNotEmpty()) {contact.name}
                else if(contact.phoneNumber.isNotEmpty()){contact.phoneNumber}
                else ""
            Text(text =userData, style = TextStyle(fontWeight = FontWeight.Bold), modifier =  Modifier.padding(start = 5.dp))
            Text(text = "..",modifier =  Modifier.padding(start = 5.dp))

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreenTopBar() {
    Row(
        modifier = Modifier.padding(start = 15.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
    ) {
        Text(
            text = "Chat App", style = TextStyle(
                color = DARK_GREEN,
                fontSize = 30.sp, fontWeight = FontWeight.Bold
            )
        )
    }
}