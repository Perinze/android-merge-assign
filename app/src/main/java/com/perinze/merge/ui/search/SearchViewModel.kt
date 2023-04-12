package com.perinze.merge.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _result = MutableLiveData<List<SearchResult>>().apply {
        value = emptyList()
    }
    val result: LiveData<List<SearchResult>> = _result

    fun search(text: String) {
        val url = "http://sousuo.gov.cn/s.htm?q=$text&t=zhengcelibrary&orpro="
        Log.d("url", url)
    }
}