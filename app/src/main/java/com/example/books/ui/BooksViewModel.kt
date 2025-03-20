package com.example.books.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.books.BooksApplication
import com.example.books.data.Book
import com.example.books.data.BooksRepository
import com.example.books.ui.components.SnackbarType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class BooksUiState {
    object Search : BooksUiState()
    object Loading : BooksUiState()
    data class Success(val booksSearch: List<Book>) : BooksUiState()
    data class BookDetails(val book: Book) : BooksUiState()
    object Error : BooksUiState()
    object Favorites : BooksUiState()
}

data class SnackbarMessage(val message: String, val type: SnackbarType)


sealed class NavigationEvent {
    object SearchScreen : NavigationEvent()
    object FavoritesScreen : NavigationEvent()
}

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.SearchScreen)
    val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    private val _favoriteBooks = mutableStateListOf<Book>()
    val favoriteBooks: List<Book> get() = _favoriteBooks

    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Search)
        private set

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val _snackbarMessage = MutableStateFlow<SnackbarMessage?>(null)
    val snackbarMessage: StateFlow<SnackbarMessage?> = _snackbarMessage.asStateFlow()


    fun navigateTo(destination: NavigationEvent) {
        _navigationEvent.value = destination
        when (destination) {
            is NavigationEvent.SearchScreen -> booksUiState = BooksUiState.Search
            is NavigationEvent.FavoritesScreen -> booksUiState = BooksUiState.Favorites
        }
    }

    fun isFavorite(book: Book): Boolean {
        return _favoriteBooks.contains(book)
    }

    fun toggleFavorite(book: Book) {
        try {
            if (_favoriteBooks.contains(book)) {
                _favoriteBooks.remove(book)
                _snackbarMessage.value = SnackbarMessage(
                    message = "Книга успешно удалена из избранного",
                    type = SnackbarType.SUCCESS
                )
            } else {
                _favoriteBooks.add(book)
                _snackbarMessage.value = SnackbarMessage(
                    message = "Книга успешно добавлена в избранное",
                    type = SnackbarType.SUCCESS
                )
            }
        } catch (e: Exception) {
            _snackbarMessage.value = SnackbarMessage(
                message = if (_favoriteBooks.contains(book))
                    "Ошибка удаления из избранного"
                else
                    "Ошибка добавления в избранное",
                type = SnackbarType.ERROR
            )
        }
    }

    fun searchBooks(query: String, maxResult: Int = 40) {
        if (query.isBlank()) {
            booksUiState = BooksUiState.Search
            return
        }

        viewModelScope.launch {
            booksUiState = BooksUiState.Loading
            booksUiState = try {
                val books = booksRepository.getBooks(query, maxResult)
                if (books.isEmpty()) {
                    BooksUiState.Error
                } else {
                    BooksUiState.Success(books)
                }
            } catch (e: IOException) {
                BooksUiState.Error
            } catch (e: HttpException) {
                BooksUiState.Error
            }
        }
    }
    fun onBookClick(book: Book) {
        booksUiState = BooksUiState.BookDetails(book)
    }
    fun onBackToSearch() {
        booksUiState = BooksUiState.Search
    }
    fun updateSearchQuery(query: String) {
        _searchText.value = query
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }

        companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(booksRepository = booksRepository)
            }
        }
    }
}
