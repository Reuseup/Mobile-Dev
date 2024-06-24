package com.bangkit.resep.repository

import com.bangkit.resep.api.RetrofitInstance

class NewsRepository
{
    suspend fun getArticles(keyword:String, pageSize: Int) =
        RetrofitInstance.api.getEverything(keyword, pageSize)
}