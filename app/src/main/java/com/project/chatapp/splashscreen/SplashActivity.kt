package com.project.chatapp.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.project.chatapp.BaseActivity
import com.project.chatapp.auth.LoginActivity
import com.project.chatapp.ui.theme.ChatAppTheme
import kotlinx.coroutines.launch

class SplashActivity:BaseActivity() {
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
            // Use repeatOnLifecycle for lifecycle safety
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Collect the StateFlow
                splashScreenViewModel.isLoading.collect { isLoading ->
                    if (!isLoading) { 
                        val loginActivityIntent = Intent(context,LoginActivity::class.java)
                        startActivity(loginActivityIntent)
                    }
                }
            }
        }

        setContent {
            ChatAppTheme {
                Text(text = "Splash Screen")
            }
        }
    }

}