package com.andreick.coroutinesflow.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreick.coroutinesflow.databinding.ItemNewsArticleBinding
import com.andreick.coroutinesflow.loadImage
import com.andreick.coroutinesflow.model.NewsArticle

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder>() {

    private val newsItems = arrayListOf<NewsArticle>()

    fun onAddNewsItem(item: NewsArticle) {
        newsItems.add(0, item)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsItemViewHolder(ItemNewsArticleBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = newsItems.size

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(newsItems[position])
    }

    class NewsItemViewHolder(private val binding: ItemNewsArticleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: NewsArticle) {
            binding.newsImage.loadImage(newsItem.urlToImage)
            binding.newsAuthor.text = newsItem.author
            binding.newsTitle.text = newsItem.title
            binding.newsPublishedAt.text = newsItem.publishedAt
        }
    }
}