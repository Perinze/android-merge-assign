package com.perinze.merge

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.perinze.merge.ui.search.SearchResultAdapter
import com.perinze.merge.ui.search.SearchViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var root: ConstraintLayout
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchResultRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("search activity", "onCreate")
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        setContentView(R.layout.search_result)
        root = findViewById(R.id.search_result_view)
        searchResultRecyclerView = root.findViewById(R.id.search_result_recycler_view)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        searchResultRecyclerView.layoutManager = linearLayoutManager

        searchResultRecyclerView.adapter = SearchResultAdapter(this, this, searchViewModel.result)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        Log.d("search activity", "handleIntent invoked")
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Log.d("query", query)
                searchViewModel.search(query)
            }
        }
    }
}