package com.project.chatapp.presentation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.chatapp.BaseActivity
import com.project.chatapp.auth.LoginActivity
import com.project.chatapp.presentation.chats_screens.ChatsScreen
import com.project.chatapp.model.User
import com.project.chatapp.ui.theme.ChatAppTheme
import com.project.chatapp.presentation.status_screen.UpdatesScreen
import com.project.chatapp.presentation.viewmodels.MainAppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeScreenActivity : BaseActivity() {

    private val mainAppViewModel: MainAppViewModel by viewModels()

    val permissions  = arrayOf(android.Manifest.permission.READ_CONTACTS)

    lateinit var context: Context

    private var requestPermissionLauncher : ActivityResultLauncher<String>
            =registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            // TODO: Show Dialog to get permission
        }else{
            lifecycleScope.launch {
//                val contacts = getAllContacts(this@HomeScreenActivity)
//                mainAppViewModel.setListOfContacts(contacts)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    context=this
                    HomeScreen(mainAppViewModel,permissionGranted(),{
                        if (!permissionGranted()) {
                            //checkRequiredPermission()
                        }
                    }) {
                        startActivity(Intent(context, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    fun checkRequiredPermission(){
        if(!permissionGranted()){
            requestPermission();
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(permissions[0])
    }

    fun permissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this,permissions[0]) == PackageManager.PERMISSION_GRANTED
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(mainAppViewModel: MainAppViewModel,
               isPermissionGranted:Boolean,
               askPermission:()->Unit,
               logOut:()-> Unit) {
    askPermission()
    val context= LocalContext.current
    LaunchedEffect(isPermissionGranted) {
        if(isPermissionGranted && mainAppViewModel.listOfConnectedUser.value.isEmpty()) {
            mainAppViewModel.setListOfContacts(getAllContacts(context))
            Log.d("nfvgj","${mainAppViewModel.listOfConnectedUser.value}")
        }
    }

    val navController = rememberNavController()
    Scaffold (
        bottomBar = { HomeScreenBottomNav(navController) },
        modifier = Modifier,
    ){
        Column(
            modifier = Modifier.padding(it)
        ) {
            NavHost(navController = navController, startDestination = BottomNavItems.ChatsScreen.route) {
                composable(BottomNavItems.ChatsScreen.route) {ChatsScreen(){
                   mainAppViewModel.launchCatching {
                       try {
                           mainAppViewModel.authenticationService.signOut()
                           logOut()
                       }catch (exception: Exception){
                           Log.d("vggfh", "HomeScreen: ${exception.message}")
                       }
                   }
                } }
                composable(BottomNavItems.UpdatesScreen.route) {UpdatesScreen()}
            }
        }
    }

}

private suspend fun getAllContacts(context: Context) :List<User> {

    return withContext(Dispatchers.IO){
        val listOfUserUsers = mutableListOf<User>()
        val contentResolver = context.contentResolver
        val contactCursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        contactCursor?.let {
            while (it.moveToNext()) {
                val user = User()
                val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                user.contactId= id.toLong()
                val name =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                name?.let {
                    user.username=it;
                }
                val hasPhoneNumber =
                    it.getInt(it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                if (hasPhoneNumber > 0) {
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                        arrayOf(id),
                        null
                    )
                    phoneCursor?.use { pCursor ->
                        while (pCursor.moveToNext()) {
                            val number = pCursor.getString(
                                pCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                            number?.let {
                                user.phoneNumber = it
                            }
                            val photoIndex = pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
                            val photoUriIndex = pCursor.getString(photoIndex)
                            val photoUri =photoUriIndex?.let { Uri.parse(it) }
                            photoUri?.let { photouri->
                                user.setContactPhotoUri(photouri)
                            }
                        }

                    }
                }
                listOfUserUsers.add(user)
            }
        }
        listOfUserUsers
    }
}




@Composable
fun HomeScreenBottomNav(navController: NavHostController) {
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        BottomNavItems.values.forEachIndexed{ index,item->
            NavigationBarItem(
                icon = { Icon(
                    painter = painterResource(id = item.iconRes) ,
                    contentDescription ="",
                    modifier = Modifier
                        .size(24.dp))},
                label = { Text(text = item.label)},
                selected = (selectedItem==index),
                onClick = {
                    selectedItem=index
                    navController.navigate(item.route)
                }
            )
        }
    }
}

@Preview
@Composable
fun FloatingActionBar(onClickButton:()-> Unit={}) {
    FloatingActionButton(onClick = {
        onClickButton()
    }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "")
    }
}

