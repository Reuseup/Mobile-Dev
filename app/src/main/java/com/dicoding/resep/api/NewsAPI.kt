package com.bangkit.resep.api

import com.bangkit.resep.models.NewsResponse
import com.bangkit.resep.util.Constant.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q")
        keyword: String ="dating",
        @Query("pageSize") pageSize: Int,
        @Query("apikey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}