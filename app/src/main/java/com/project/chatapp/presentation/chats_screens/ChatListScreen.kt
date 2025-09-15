package com.project.chatapp.presentation.chats_screens


import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.project.chatapp.presentation.viewmodels.MainAppViewModel
import com.project.chatapp.model.User
import com.project.chatapp.presentation.FloatingActionBar
import com.project.chatapp.presentation.common.Constants.CONTACT_SELECTED
import com.project.chatapp.presentation.common.Constants.CURRENT_USER_UID
import com.project.chatapp.presentation.common.Constants.SELECTED_CONTACT_UID
import com.project.chatapp.ui.theme.DARK_GREEN


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    mainAppViewModel: MainAppViewModel = viewModel(),
    signOutUser: () -> Unit
) {
    val listOfConnectedUser by mainAppViewModel.listOfConnectedUser.collectAsState()
    val context = LocalContext.current
    Log.d("nfjnwf","$listOfConnectedUser")

    var queryText by remember {
        mutableStateOf("")
    }

    var dialogToAddContact by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier,
        contentWindowInsets = WindowInsets(left=0.dp, top = 0.dp, bottom = 0.dp, right = 0.dp),
        topBar = {
            if(queryText.isEmpty()){
                ChatsScreenTopBar(){
                    signOutUser()
                }
            }
        },
        floatingActionButton = {FloatingActionBar(){
            mainAppViewModel.launchCatching {
                dialogToAddContact = true
            }
        } }
    ) {
        Column(
            modifier = Modifier.padding(it)
        )
        {

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
                items(listOfConnectedUser) { user ->
                    ChatContact(user) { selectedUser ->
                        val intent = Intent(context, ChatScreenActivity::class.java)
                        intent.putExtra(SELECTED_CONTACT_UID, selectedUser.uid)
                        intent.putExtra(CURRENT_USER_UID, mainAppViewModel.authenticationService.currentUserId )
                        context.startActivity(intent)
                    }
                }
            }


            if(dialogToAddContact){
                var connectionFriendEmail by remember {
                    mutableStateOf("")
                }
                AlertDialog(
                    title = {Text("Find User")},
                    text = {
                        Column (
                            modifier = Modifier.fillMaxWidth()
                        ){
                            OutlinedTextField(
                                value = connectionFriendEmail,
                                onValueChange = { value->
                                    connectionFriendEmail = value
                                }
                            )
                        }
                    },
                    onDismissRequest = {

                    },
                    confirmButton = {
                        Button(onClick = {
                            Log.d("bvjf", "ChatsScreen: ${mainAppViewModel.authenticationService.currentUserId}")
                            mainAppViewModel.addContactToUser(
                                mainAppViewModel.authenticationService.currentUserId,
                                connectionFriendEmail
                            ){
                                Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                            }
                            dialogToAddContact=false
                        }) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            dialogToAddContact=false
                        }) {
                            Text("Cancel")
                        }

                    }
                )
            }

        }

    }
}

//@Preview
@Composable
fun ChatContact(user: User= User(username = "dummy"), onClickContact:(User)->Unit={}) {
    Row (
        Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(10.dp)
            .clickable {
                onClickContact(user)
            }
    ) {
         when(user.getContactPhotoUri()){
             null->Image(
                 imageVector = Icons.Filled.AccountCircle,
                 contentDescription = "",modifier = Modifier
                     .clip(CircleShape)
                     .width(45.dp)
                     .height(45.dp))
             is Uri->{
                 AsyncImage(
                     model = user.getContactPhotoUri(),
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
                if(user.username.isNotEmpty()) {user.username}
                else if(user.phoneNumber.isNotEmpty()){user.phoneNumber}
                else ""
            Text(text =userData, style = TextStyle(fontWeight = FontWeight.Bold), modifier =  Modifier.padding(start = 5.dp))
            Text(text = "..",modifier =  Modifier.padding(start = 5.dp))

        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreenTopBar(signOutUser : ()-> Unit={}) {
    var showDropdown by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(
            text = "Chat App", style = TextStyle(
                color = DARK_GREEN,
                fontSize = 30.sp, fontWeight = FontWeight.Bold
            )
        )

        IconButton(
            onClick = {
                showDropdown=!showDropdown
            },
            modifier = Modifier
        ) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
        }

        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false }
        ) {
            DropdownMenuItem(
                text = { Text("Log Out") },
                onClick = {
                    signOutUser()
                }
            )
        }

    }


}