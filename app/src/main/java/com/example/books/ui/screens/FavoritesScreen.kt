package com.example.books.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.books.R
import com.example.books.data.Book

@Composable
fun FavoritesScreen(
    favoriteBooks: List<Book>,
    onFavoriteClick: (Book) -> Unit,
    onBookClick: (Book) -> Unit,
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateToSearch) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Назад",
                    modifier = Modifier.requiredSize(24.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f)) // Центрируем текст
            Text(
                text = "Избранное",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.weight(1f)) // Центрируем текст
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(favoriteBooks.size) { index ->
                val book = favoriteBooks[index]
                BooksCard(
                    book = book,
                    isFavorite = true,
                    onFavoriteClick = onFavoriteClick,
                    onBookClick = {
                        onNavigateToSearch()
                        onBookClick(book)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onNavigateToSearch()
                            onBookClick(book)
                        }
                )
            }
        }
    }
}