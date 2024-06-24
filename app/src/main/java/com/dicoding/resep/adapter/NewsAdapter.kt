package com.bangkit.resep.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.resep.R
import com.bangkit.resep.models.Article
import com.bumptech.glide.Glide

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var articleImage: ImageView
    private lateinit var articleSource: TextView
    private lateinit var articleTitle: TextView
    private lateinit var articleDateTime: TextView

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }

    }
     val differ = AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.article_row, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        // Bind data to views in your ViewHolder
        // Example:
        holder.itemView.apply {
            // Set article data to views here
            // Example:
            articleImage = findViewById(R.id.article_iv)
            articleSource = findViewById(R.id.article_source)
            articleTitle = findViewById(R.id.article_title)
            articleDateTime = findViewById(R.id.articleDateTime)

            Glide.with(this).load(article.urlToImage).into(articleImage)
            articleSource.text = article.source.name
            articleTitle.text = article.title
            articleDateTime.text = article.publishedAt

            // Set click listener if needed
            setOnClickListener {
                onItemClickListener?.invoke(article)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Optional: Set item click listener
    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    // Update data method for AsyncListDiffer
    fun submitList(list: List<Article>) {
        differ.submitList(list)
    }
}
