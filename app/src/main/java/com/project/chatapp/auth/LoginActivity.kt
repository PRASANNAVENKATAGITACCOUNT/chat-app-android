package com.project.chatapp.auth

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.project.chatapp.BaseActivity
import com.project.chatapp.ui.theme.ChatAppTheme

class LoginActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {

            }
        }
    }
}