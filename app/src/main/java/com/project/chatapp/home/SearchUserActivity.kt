package com.project.chatapp.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.project.chatapp.BaseActivity
import com.project.chatapp.ui.theme.ChatAppTheme

class SearchUserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchUserActivityPreview() {
    ChatAppTheme {

    }
}
