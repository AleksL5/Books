package com.example.books.ui.components

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.books.R
import com.example.books.data.Book

@Composable
fun FavoriteButton(
    book: Book,
    isFavorite: Boolean,
    onFavoriteClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1f,
        animationSpec = tween(durationMillis = 300, easing = EaseInOut)
    )

    IconButton(
        onClick = { onFavoriteClick(book) },
        modifier = modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .graphicsLayer(scaleX = scale, scaleY = scale)
    ) {
        Icon(
            painter = painterResource(
                id = if (isFavorite) R.drawable.ic_favorite_focused else R.drawable.ic_favorite
            ),
            contentDescription = stringResource(R.string.favorite),
            tint = if (isFavorite) Color.Red else Color.LightGray,
            modifier = Modifier.size(25.dp)
        )
    }
}