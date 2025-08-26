package com.project.chatapp.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.project.chatapp.auth.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginActivityScreen(loginViewModel: LoginViewModel) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
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
            val pagerState = rememberPagerState(pageCount = { listOfTab.size })
            val scope = rememberCoroutineScope()
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
                        //val selected = tabIndex == index
                        val selected = pagerState.currentPage == index
                        Tab(text = { Text(title) },
                            selected = selected,
                            onClick = {
                                //tabIndex = index
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                           },
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
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) {page->
                    when (page) {
                        0 -> LoginScreen()
                        1 -> RegisterScreen()
                    }
                }


            }

        }
    }
}

sealed class TabColor (val color: Color){
    object SelectedTabColor: TabColor(Color.Green)
    object UnSelectedTabColor: TabColor(Color.White)
}
