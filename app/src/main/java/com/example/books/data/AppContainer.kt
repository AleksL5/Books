package com.example.books.data


import com.example.books.network.BookService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksRepository: BooksRepository
}

class DefaultAppContainer: AppContainer{
    private val BASE_URL = "https://www.googleapis.com/books/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create()) // С помощью данного конвертера объект retrofit будет конвертировать ответы от json сервера в объектры дата классов
        .baseUrl(BASE_URL)
        .build()


    private val retrofitService: BookService by lazy { // создаём вызов к API с помощью объекта retrofit
        retrofit.create(BookService::class.java)
    }
    override val booksRepository: BooksRepository by lazy {
        NetworkBooksRepository(retrofitService)
    }
}