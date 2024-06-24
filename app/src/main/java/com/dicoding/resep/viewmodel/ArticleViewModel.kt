package com.bangkit.resep.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.resep.models.NewsResponse
import com.bangkit.resep.repository.NewsRepository
import com.bangkit.resep.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ArticleViewModel(app: Application, private val newsRepository: NewsRepository) : AndroidViewModel(app) {
    val article: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var articlePage = 1
    private var articleResponse: NewsResponse? = null

    init {
        getHeadlines("anxiety") // Example keyword, you can change it as needed
    }

    private fun getHeadlines(keyword: String) = viewModelScope.launch {
        headlinesInternet(keyword,pageSize = 15)
    }

    private fun handleArticleResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                articlePage++
                if (articleResponse == null) {
                    articleResponse = resultResponse
                } else {
                    val oldArticles = articleResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(articleResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun internetConnection(context: Context): Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    private suspend fun headlinesInternet(keyword: String, pageSize: Int) {
        article.postValue(Resource.Loading())
        try {
            if (internetConnection(this.getApplication())) {
                val response = newsRepository.getArticles(keyword, pageSize) // Pass all parameters
                article.postValue(handleArticleResponse(response))
            } else {
                article.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> article.postValue(Resource.Error("Unable to connect"))
                else -> article.postValue(Resource.Error("No signal"))
            }
        }
    }
}
