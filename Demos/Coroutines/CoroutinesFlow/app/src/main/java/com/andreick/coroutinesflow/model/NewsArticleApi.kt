package com.andreick.coroutinesflow.model

import retrofit2.http.GET

interface NewsArticleApi {

    @GET("news.json")
    suspend fun getNews(): List<NewsArticle>
}