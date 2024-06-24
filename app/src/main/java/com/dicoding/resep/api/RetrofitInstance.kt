package com.bangkit.resep.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        private val retrofitNews by lazy {
            Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        private val retrofitChatbot by lazy {
            Retrofit.Builder()
                .baseUrl("https://chatbotapi-3st65okbvq-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: NewsAPI by lazy {
            retrofitNews.create(NewsAPI::class.java)
        }

        val chatbotApi: ChatAPI by lazy {
            retrofitChatbot.create(ChatAPI::class.java)
        }
    }
}
