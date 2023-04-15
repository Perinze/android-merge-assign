package com.perinze.merge.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class SearchViewModel : ViewModel() {

    private val _result = MutableLiveData<List<SearchResult>>().apply {
        value = emptyList()
    }
    val result: LiveData<List<SearchResult>> = _result

    private val queryUrlTemplate = "http://sousuo.gov.cn/s.htm?q=%s&t=zhengcelibrary&orpro="

    fun search(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val url = queryUrlTemplate.format(text)
            Log.d("url", url)

            val doc: Document = Jsoup.connect(url).get()
            val result: Elements = doc.select("div[dys_middle_result_content_item]")
            Log.d("result", result.toString())

            _result.postValue(emptyList())
        }
    }
}