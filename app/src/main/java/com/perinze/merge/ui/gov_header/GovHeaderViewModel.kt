package com.perinze.merge.ui.gov_header

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class GovHeaderViewModel : ViewModel() {

    private val _data = MutableLiveData<List<GovHeader>>().apply {
        value = emptyList()
    }
    val data: LiveData<List<GovHeader>> = _data

    fun sync() {
        Thread {
            val stringBuilder = java.lang.StringBuilder()
            val subtitleList: ArrayList<GovHeader> = ArrayList()
            try {
                val doc: Document = Jsoup.connect("http://www.gov.cn").get()
                Log.d("doc", doc.toString())
                val slides: Elements = doc.select("div[class=slider_carousel]")
                Log.d("slides", slides.toString())
                val images: Elements = slides.select("img")
                val h4s: Elements = slides.select("h4")
                Log.d("images", images.toString())
                Log.d("h4s", h4s.toString())
                for (i in 0 until images.size) {
                    val image = images[i]
                    val link = h4s[i].select("a[href]")
                    Log.d("img", image.toString())
                    Log.d("link", link.toString())
                    subtitleList.add(GovHeader(link.text(), "http://www.gov.cn" + image.attr("src"), link.attr("href")))
                }
            } catch (e: IOException) {
                stringBuilder.append("Error: ").append(e.message).append("\n")
            }
            Log.d("sz", subtitleList.size.toString())
            _data.postValue(subtitleList)
        }.start()
    }
}