package com.project.chatapp.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.project.chatapp.BaseActivity
import com.project.chatapp.R
import com.project.chatapp.auth.screens.LoginScreen
import com.project.chatapp.auth.screens.RegisterScreen
import com.project.chatapp.home.HomeScreenActivity
import com.project.chatapp.splashscreen.SplashScreenViewModel
import com.project.chatapp.ui.theme.ChatAppTheme


class LoginActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {
            ChatAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginActivityScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginActivityScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column (
            modifier = Modifier.padding(it)
        ) {
            var tabIndex by remember {
                mutableStateOf(0)
            }
            val listOfTab = listOf<String>("Login","Register")
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                TabRow(selectedTabIndex = tabIndex) {
                    listOfTab.forEachIndexed { index, title ->
                        Tab(text = { Text(title) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index }
                        )
                    }
                }
                when (tabIndex) {
                    0 -> LoginScreen()
                    1 -> RegisterScreen()
                }

            }

        }
    }
}

