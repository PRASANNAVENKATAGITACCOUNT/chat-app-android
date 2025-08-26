package com.project.chatapp.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.project.chatapp.BaseActivity
import com.project.chatapp.auth.viewmodels.SplashScreenViewModel
import com.project.chatapp.main_app.HomeScreenActivity
import com.project.chatapp.ui.theme.ChatAppTheme
import kotlinx.coroutines.launch

class SplashActivity: BaseActivity() {
    private val splashScreenViewModel : SplashScreenViewModel by viewModels()
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        context=this
        splashScreen.setKeepOnScreenCondition {
            splashScreenViewModel.isLoading.value
        }

        lifecycleScope.launch {
            if(splashScreenViewModel.isUserPresent()){
                val homeActivityIntent = Intent(context, HomeScreenActivity::class.java)
                startActivity(homeActivityIntent)
            }else{
                val loginActivityIntent = Intent(context, LoginActivity::class.java)
                startActivity(loginActivityIntent)
            }
        }

        setContent {
            ChatAppTheme {
                Text(text = "Splash Screen")
            }
        }
    }

}