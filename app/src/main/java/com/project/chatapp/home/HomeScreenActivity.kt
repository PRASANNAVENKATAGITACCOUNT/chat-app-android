package com.project.chatapp.home

import android.content.Context
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.chatapp.BaseActivity
import com.project.chatapp.R
import com.project.chatapp.auth.screens.LoginScreen
import com.project.chatapp.calls.CallsScreen
import com.project.chatapp.chats.ChatsScreen
import com.project.chatapp.communities.CommunitiesScreen
import com.project.chatapp.model.Contact
import com.project.chatapp.splashscreen.SplashScreenViewModel
import com.project.chatapp.ui.theme.ChatAppTheme
import com.project.chatapp.updates.UpdatesScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenActivity : BaseActivity() {

    private val homeScreenViewModel:HomeScreenViewModel by viewModels()

    val permissions  = arrayOf(android.Manifest.permission.READ_CONTACTS)

    private var requestPermissionLauncher : ActivityResultLauncher<String>
            =registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            // TODO: Show Dialog to get permission
        }else{
            lifecycleScope.launch {
                val contacts = getAllContacts(this@HomeScreenActivity)
                homeScreenViewModel.setListOfContacts(contacts)
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
                    HomeScreen(homeScreenViewModel,permissionGranted()) {
                        if (!permissionGranted()) {
                            checkRequiredPermission()
                        }
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
fun HomeScreen(homeScreenViewModel:HomeScreenViewModel,isPermissionGranted:Boolean, askPermission:()->Unit) {
    askPermission()
    val context= LocalContext.current
    LaunchedEffect(isPermissionGranted) {
        if(isPermissionGranted && homeScreenViewModel.listOfContacts.value.isEmpty()) {
            homeScreenViewModel.setListOfContacts(getAllContacts(context))
            Log.d("nfvgj","${homeScreenViewModel.listOfContacts.value}")
        }
    }

    val navController = rememberNavController()
    Scaffold (
        bottomBar = { HomeScreenBottomNav(navController) }
    ){
        Column(
            modifier = Modifier.padding(it)
        ) {
            NavHost(navController = navController, startDestination = BottomNavItems.ChatsScreen.route) {
                composable(BottomNavItems.ChatsScreen.route) {ChatsScreen(homeScreenViewModel.listOfContacts)}
                composable(BottomNavItems.UpdatesScreen.route) {UpdatesScreen()}
                composable(BottomNavItems.CommunitiesScreen.route) {CommunitiesScreen() }
                composable(BottomNavItems.CallsScreen.route) { CallsScreen()}
            }
        }
    }

}

private suspend fun getAllContacts(context: Context) :List<Contact> {

    return withContext(Dispatchers.IO){
        val listOfUserContacts = mutableListOf<Contact>()
        val contentResolver = context.contentResolver
        val contactCursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        contactCursor?.let {
            while (it.moveToNext()) {
                val contact = Contact()
                val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                contact.id = id.toLong()
                val name =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                name?.let {
                    contact.name=it;
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
                                contact.phoneNumber = it
                            }
                            val photoIndex = pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
                            val photoUriIndex = pCursor.getString(photoIndex)
                            val photoUri =photoUriIndex?.let { Uri.parse(it) }
                            photoUri?.let { photouri->
                                contact.setContactPhotoUri(photouri)
                            }
                        }

                    }
                }
                listOfUserContacts.add(contact)
            }
        }
        listOfUserContacts
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

