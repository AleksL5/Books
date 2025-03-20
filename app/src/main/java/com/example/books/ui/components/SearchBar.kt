package com.example.books.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.books.R
import com.example.books.ui.theme.Black
import com.example.books.ui.theme.Grey
import com.example.books.ui.theme.LightGreyTransparent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onAwayFromSearchBar: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val debounceJob = remember { mutableStateOf<Job?>(null) }

    val commonModifier = Modifier
        .background(LightGreyTransparent, shape = RoundedCornerShape(14.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = commonModifier
                .weight(1f)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Поиск",
                modifier = Modifier.padding(start = 12.dp),
                tint = Color.Unspecified
            )

            BasicTextField(
                value = text,
                onValueChange = { newText ->
                    onTextChange(newText)

                    debounceJob.value?.cancel()
                    debounceJob.value = coroutineScope.launch {
                        delay(2000)
                        if (newText.isNotBlank()) {
                            onSearchClicked(newText)
                        }
                    }
                },
                singleLine = true,
                textStyle = TextStyle(color = Black, fontSize = 16.sp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onSearchClicked(text)
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .onFocusChanged {
                        if (!it.isFocused) {
                            onAwayFromSearchBar()
                        }
                    },
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (text.isEmpty()) {
                            Text("Поиск", color = Grey, fontSize = 16.sp)
                        }
                        innerTextField()
                    }
                }
            )

            if (text.isNotEmpty()) {
                IconButton(onClick = { onTextChange("") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Очистить",
                        tint = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        IconButton(
            onClick = { /* Открытие параметров поиска */ },
            modifier = commonModifier
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Фильтр",
                tint = Color.Unspecified
            )
        }
    }
}

