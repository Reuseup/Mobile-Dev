package com.bangkit.resep.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.resep.R
import com.bangkit.resep.adapter.NewsAdapter
import com.bangkit.resep.repository.NewsRepository
import com.bangkit.resep.util.Resource
import com.bangkit.resep.viewmodel.ArticleViewModel
import com.bangkit.resep.viewmodel.ArticleViewModelFactory

class ArticleFragment : Fragment() {

    private lateinit var viewModel: ArticleViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsRepository: NewsRepository

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)

        // Initialize RecyclerView and Adapter here
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_article)
        newsAdapter = NewsAdapter()
        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the repository and the factory
        newsRepository = NewsRepository()
        val viewModelFactory = ArticleViewModelFactory(requireActivity().application, newsRepository)

        // Initialize the ViewModel using the factory
        viewModel = ViewModelProvider(this, viewModelFactory)[ArticleViewModel::class.java]

        viewModel.article.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        newsAdapter.notifyDataSetChanged()
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    // Show a loading spinner or something similar
                }
            }
        }

        return view
    }
}

