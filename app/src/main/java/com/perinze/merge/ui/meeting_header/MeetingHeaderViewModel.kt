package com.perinze.merge.ui.meeting_header

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class MeetingHeaderViewModel : ViewModel() {

    private val _data = MutableLiveData<List<MeetingHeader>>().apply {
        value = emptyList()
    }
    val data: LiveData<List<MeetingHeader>> = _data

    fun sync() {
        Thread {
            val stringBuilder = java.lang.StringBuilder()
            val subtitleList: ArrayList<MeetingHeader> = ArrayList()
            try {
                val doc: Document = Jsoup.connect("http://www.gov.cn/zhuanti/2023qglh/index.htm").get()
                val images: Elements = doc.select("div[class=pannel-image]")
                val subtitles: Elements = doc.select("div[class=subtitle]")
                // class="item slidesjs-slide"
                Log.d("slides", images.toString())
                Log.d("slides", subtitles.toString())
                for (i in 0 until images.size) {
                    val image = images[i].select("img")
                    val link = subtitles[i].select("a[href]")
                    Log.d("img", image.toString())
                    Log.d("link", link.toString())
                    subtitleList.add(MeetingHeader(link.text(), "http://www.gov.cn" + image.attr("src"), link.attr("href")))
                }
            } catch (e: IOException) {
                stringBuilder.append("Error: ").append(e.message).append("\n")
            }
            Log.d("image src", subtitleList[0].img)
            _data.postValue(subtitleList)
        }.start()
    }
}