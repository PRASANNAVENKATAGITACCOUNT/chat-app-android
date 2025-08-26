package com.project.chatapp.main_app.status_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun UpdatesScreen() {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Updates", fontSize = 35.sp, fontWeight = FontWeight.Bold)
    }

}