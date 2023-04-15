package com.perinze.merge.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class SearchViewModel : ViewModel() {

    private val _result = MutableLiveData<List<SearchResult>>().apply {
        value = emptyList()
    }
    val result: LiveData<List<SearchResult>> = _result

    private val queryUrlTemplate = "http://sousuo.gov.cn/s.htm?t=zhengcelibrary&q=%s&timetype=&mintime=&maxtime=&sort=&sortType=&searchfield=&pcodeJiguan=&bmfl=&childtype=&subchildtype=&tsbq=&pubtimeyear=&puborg=&pcodeYear=&pcodeNum=&filetype=&p=&n=&orpro=&inpro="

    fun search(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val url = queryUrlTemplate.format(text)
            Log.d("url", url)

            val doc: Document = Jsoup.connect(url).get()
            //Log.d("doc", doc.toString())
            val result: Elements = doc.select("div[class=dys_middle_result_content_item]")
            Log.d("result", result.toString())

            val resultList: List<SearchResult> = result.map {
                val href: Element = it.select("a[href]")[0]
                val h5: Element = href.select("h5[class=dysMiddleResultConItemTitle]")[0]
                val p: Element = it.select("p[class=dysMiddleResultConItemMemo]")[0]
                val relevant: Elements = it.select("span")
                println(relevant)
                SearchResult(
                    h5.text(),
                    p.text(),
                    relevant[0].text(),
                    relevant[1].text(),
                    href.attr("href"),
                )
            }

            Log.d("result list", resultList.toString())

            _result.postValue(resultList)
        }
    }
}