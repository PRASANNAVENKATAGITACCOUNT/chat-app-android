package com.project.chatapp.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.project.chatapp.BaseActivity
import com.project.chatapp.R
import com.project.chatapp.auth.screens.LoginScreen
import com.project.chatapp.auth.screens.RegisterScreen
import com.project.chatapp.main_app.HomeScreenActivity
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
        modifier = Modifier.fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues()),
    ) {
        Column (
            modifier = Modifier
                .padding(it)
        ) {
            var tabIndex by remember {
                mutableStateOf(0)
            }
            val listOfTab = listOf<String>("Login","Register")
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                TabRow(
                    selectedTabIndex = tabIndex,
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(50)),
                    indicator = { tabPositions: List<TabPosition> ->
                        Box {}
                    },
                    containerColor = Color.White,
                    contentColor = Color.Black
                    ) {
                    listOfTab.forEachIndexed { index, title ->
                        val selected = tabIndex == index
                        Tab(text = { Text(title) },
                            selected = selected,
                            onClick = { tabIndex = index },
                            modifier =  if (selected) {
                                Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(
                                    TabColor.SelectedTabColor.color
                                    )
                            }
                            else {
                                Modifier
                                    .background(
                                    TabColor.UnSelectedTabColor.color
                                )
                            }
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

sealed class TabColor (val color: Color){
    object SelectedTabColor: TabColor(Color.Green)
    object UnSelectedTabColor: TabColor(Color.White)
}



