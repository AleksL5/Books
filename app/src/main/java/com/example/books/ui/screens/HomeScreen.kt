package com.example.books.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.books.data.Book
import com.example.books.ui.BooksUiState
import com.example.books.ui.BooksViewModel
import com.example.books.ui.NavigationEvent

@Composable
fun HomeScreen(
    booksUiState: BooksUiState,
    booksViewModel: BooksViewModel,
    retryAction: () -> Unit,
    onFavoriteClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    when (booksUiState) {
        is BooksUiState.Search -> SearchScreen()
        is BooksUiState.Loading -> LoadingScreen(modifier)
        is BooksUiState.Success -> BooksGridScreen(
            books = booksUiState.booksSearch,
            booksViewModel = booksViewModel,
            onFavoriteClick = onFavoriteClick,
            onBookClick = { book -> booksViewModel.onBookClick(book) },
            modifier = modifier
        )
        is BooksUiState.Favorites -> FavoritesScreen(
            favoriteBooks = booksViewModel.favoriteBooks,
            onFavoriteClick = { booksViewModel.toggleFavorite(it) },
            onBookClick = { booksViewModel.onBookClick(it) },
            onNavigateToSearch = { booksViewModel.navigateTo(NavigationEvent.SearchScreen) }
        )
        is BooksUiState.BookDetails -> BookDetailScreen(
            book = booksUiState.book,
            onBackClick = { booksViewModel.onBackToSearch() },
            onFavoriteClick = onFavoriteClick,
            isFavorite = booksViewModel.isFavorite(booksUiState.book)
        )
        is BooksUiState.Error -> ErrorScreen(retryAction = retryAction, modifier)
    }
}
