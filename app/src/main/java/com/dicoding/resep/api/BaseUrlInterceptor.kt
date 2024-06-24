package com.bangkit.resep.api

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originalHttpUrl: HttpUrl = request.url

        val newBaseUrl = when {
            originalHttpUrl.encodedPath.startsWith("/respond") -> {
                "https://chatbotapi-3st65okbvq-et.a.run.app/".toHttpUrlOrNull()
            }
            else -> {
                "https://newsapi.org/".toHttpUrlOrNull()
            }
        }

        val newUrl = newBaseUrl?.scheme?.let {
            newBaseUrl.host.let { it1 ->
                newBaseUrl?.port?.let { it2 ->
                    originalHttpUrl.newBuilder()
                        .scheme(it)
                        .host(it1)
                        .port(it2)
                        .build()
                }
            }
        }

        request = newUrl?.let { request.newBuilder().url(it).build() }!!
        return chain.proceed(request)
    }
}
