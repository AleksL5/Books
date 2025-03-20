package com.example.books.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.books.R
import com.example.books.ui.NavigationEvent


@Composable
fun Navigation(
    currentTab: NavigationEvent,
    onTabSelected: (NavigationEvent) -> Unit
) {
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = currentTab is NavigationEvent.SearchScreen,
            onClick = { onTabSelected(NavigationEvent.SearchScreen) },
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (currentTab is NavigationEvent.SearchScreen)
                            R.drawable.ic_search_focused
                        else
                            R.drawable.ic_search
                    ),
                    contentDescription = "Поиск",
                    modifier = Modifier.size(32.dp),
                    tint = if (currentTab is NavigationEvent.SearchScreen)
                        Color(0xFF00ACFF)
                    else
                        Color.LightGray
                )
            },
            label = {
                Text(
                    text = "Поиск",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (currentTab is NavigationEvent.SearchScreen)
                        Color(0xFF00ACFF)
                    else
                        Color.LightGray
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentTab is NavigationEvent.FavoritesScreen,
            onClick = { onTabSelected(NavigationEvent.FavoritesScreen) },
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (currentTab is NavigationEvent.FavoritesScreen)
                            R.drawable.ic_favorite_focused
                        else
                            R.drawable.ic_favorite
                    ),
                    contentDescription = "Избранное",
                    modifier = Modifier.size(32.dp),
                    tint = if (currentTab is NavigationEvent.FavoritesScreen)
                        Color(0xFF00ACFF)
                    else
                        Color.LightGray
                )
            },
            label = {
                Text(
                    text = "Избранное",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (currentTab is NavigationEvent.FavoritesScreen)
                        Color(0xFF00ACFF)
                    else
                        Color.LightGray
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}
