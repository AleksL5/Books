package com.example.books.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.books.R
import com.example.books.data.Book

@Composable
fun BookDetailScreen(
    book: Book,
    onBackClick: () -> Unit,
    onFavoriteClick: (Book) -> Unit,
    isFavorite: Boolean
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Назад"
                    )
                }

                IconButton(onClick = { onFavoriteClick(book) }) {
                    Icon(
                        painter = painterResource(
                            id = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_focused
                        ),
                        contentDescription = "Избранное",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.imageLink?.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_book_96),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(screenHeight * 0.4f)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            book.authors?.let { authors ->
                Text(
                    modifier = Modifier.padding(41.dp, 1.dp, 41.dp, 1.dp),
                    text = authors.joinToString(", "),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                    textAlign = TextAlign.Center
                )
            }

            book.title?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(41.dp, 1.dp, 41.dp, 1.dp),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
            }

            book.publishedDate?.let {
                Text(
                    text = "$it г.",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.padding(41.dp, 1.dp, 41.dp, 105.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column{
                    Text(
                        text = "Описание",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = book.description ?: "Нет данных",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
