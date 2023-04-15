package com.perinze.merge

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.perinze.merge.ui.search.SearchResult
import com.perinze.merge.ui.search.SearchViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class SearchActivity : AppCompatActivity() {
    private lateinit var root: TextView
    private lateinit var searchViewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("search activity", "onCreate")
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        setContentView(R.layout.search_result)
        root = findViewById(R.id.search_result_view)

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