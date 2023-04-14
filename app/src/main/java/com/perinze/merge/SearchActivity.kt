package com.perinze.merge

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView

class SearchActivity : Activity() {
    private lateinit var root: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_result)
        root = findViewById<TextView>(R.id.search_result_view)
        Log.d("search activity", "onCreate")
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
                root.text = query
            }
        }
    }
}