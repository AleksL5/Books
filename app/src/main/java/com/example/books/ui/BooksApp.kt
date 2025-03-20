package com.example.books.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.books.ui.components.CustomSnackbar
import com.example.books.ui.components.Navigation
import com.example.books.ui.screens.HomeScreen
import com.example.books.ui.components.SearchAppBar
import com.example.books.ui.screens.FavoritesScreen
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BooksApp(modifier: Modifier = Modifier) {
    val booksViewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory)
    val navController = rememberNavController()
    val booksUiState = booksViewModel.booksUiState
    val snackbarMessage by booksViewModel.snackbarMessage.collectAsState()


    LaunchedEffect(booksViewModel.navigationEvent) {
        booksViewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.SearchScreen -> navController.navigate("search") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }

                is NavigationEvent.FavoritesScreen -> navController.navigate("favorites") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
        topBar = {
            if (booksUiState is BooksUiState.Search || booksUiState is BooksUiState.Success || booksUiState is BooksUiState.Loading || booksUiState is BooksUiState.Error) {
                SearchAppBar(
                    text = booksViewModel.searchText.value,
                    onTextChange = { booksViewModel.updateSearchQuery(it) },
                    onSearchClicked = { booksViewModel.searchBooks(it) },
                    onAwayFromSearchBar = {}
                )
            }
        },
        bottomBar = {
            Navigation(
                currentTab = booksViewModel.navigationEvent.collectAsState().value,
                onTabSelected = { booksViewModel.navigateTo(it) }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "search",
            modifier = Modifier.fillMaxSize()
                .fillMaxSize()
        ) {
            composable("search") {
                HomeScreen(
                    booksUiState = booksViewModel.booksUiState,
                    booksViewModel = booksViewModel,
                    retryAction = { booksViewModel.searchBooks("") },
                    onFavoriteClick = { booksViewModel.toggleFavorite(it) }
                )
            }
            composable("favorites") {
                FavoritesScreen(
                    favoriteBooks = booksViewModel.favoriteBooks,
                    onFavoriteClick = { booksViewModel.toggleFavorite(it) },
                    onBookClick = { booksViewModel.onBookClick(it) },
                    onNavigateToSearch = { booksViewModel.navigateTo(NavigationEvent.SearchScreen) }
                )
            }
        }
    }
    snackbarMessage?.let { message ->
        CustomSnackbar(
            message = message.message,
            type = message.type,
            onDismiss = { booksViewModel.clearSnackbar() }
        )
    }
    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            delay(3000)
            booksViewModel.clearSnackbar()
        }
    }
}



