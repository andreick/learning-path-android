package com.andreick.coroutinesflow.model

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsArticleService {

    private const val BASE_URL = "https://raw.githubusercontent.com/DevTides/NewsApi/master/"
    private const val NEWS_DELAY = 3000L

    private val newsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsArticleApi::class.java)

    fun getNewsArticles(): Flow<NewsArticle> {
        return flow {
            newsApi.getNews().forEach {
                emit(it)
                delay(NEWS_DELAY)
            }
        }
    }
}