package com.example.books.network


import com.example.books.BooksShelf
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("volumes")
    suspend fun bookSearch(
        @Query("q") searchQuery: String,
        @Query("maxResults") maxResult: Int
    ): BooksShelf
}