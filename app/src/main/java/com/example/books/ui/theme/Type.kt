package com.example.books.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.books.R

val RobotoFont = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_medium, FontWeight.Medium)
)

val Typography = Typography(
    displayLarge = TextStyle( // Основной крупный заголовок (Font)
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp
    ),
    headlineMedium = TextStyle( // Второстепенный заголовок (Roboto)
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    titleLarge = TextStyle( // Важный заголовок (Bold_18)
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    titleMedium = TextStyle( // Подзаголовок (Regular_18)
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle( // Обычный текст (Bold_16)
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle( // Основной текст (Regular_16)
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle( // Мелкий текст (Regular_14)
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle( // Подписи, кнопки (Medium_12)
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)