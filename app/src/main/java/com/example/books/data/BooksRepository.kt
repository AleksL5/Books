package com.example.books.data

import com.example.books.network.BookService

interface BooksRepository {
    suspend fun getBooks(query: String, maxResult: Int): List<Book>
}

class NetworkBooksRepository(
    private val bookService: BookService
): BooksRepository{
    override suspend fun getBooks(
        query: String,
        maxResult: Int
    ):List<Book> = bookService.bookSearch(query,maxResult).items.map { items ->
        Book(
            title = items.volumeInfo?.title,
            previewLink = items.volumeInfo?.previewLink,
            authors = items.volumeInfo?.authors,
            imageLink = items.volumeInfo?.imageLinks?.thumbnail,
            description = items.volumeInfo?.description,
            publishedDate = items.volumeInfo?.publishedDate
        )
    }
}