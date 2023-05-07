package com.perinze.merge.ui.article

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.GONE
import android.widget.SeekBar.INVISIBLE
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.ElementNotFoundException
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.html.HtmlVideo
import com.perinze.merge.databinding.ActivityArticleBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.logging.Level
import java.util.logging.Logger

class ArticleActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityArticleBinding

    private lateinit var videoView: VideoView
    private lateinit var seekBar: SeekBar
    private val handler = Handler(Looper.getMainLooper())

    private val update = object : Runnable {
        override fun run() {
            seekBar.max = videoView.duration
            seekBar.progress = videoView.currentPosition
            handler.postDelayed(this, 100)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        videoView = viewBinding.videoView
        seekBar = viewBinding.seekBar
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar) {
                handler.removeCallbacks(update)
            }

            override fun onStopTrackingTouch(p0: SeekBar) {
                handler.removeCallbacks(update)
                videoView.seekTo(p0.progress)
                update.run()
            }
        })
        videoView.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
            } else {
                videoView.start()
            }
        }

        val intent = intent
        val url = intent.getStringExtra("url")
        fetchContent(url!!)
    }

    private fun fetchContent(url: String) =
        Thread {
            Logger.getLogger("com.gargoylesoftware.htmlunit").level = Level.OFF
            Log.d("url", url)

            val doc: Document = Jsoup.connect(url).get()
            val title = doc.select("h1")[0].text()
            val paragraph = doc.select("p").map {
                it.text()
            }.reduce { acc, s ->
                acc + s + '\n'
            }.toString()

            val elem = doc.select("iframe[id=iframe1]")
            val hasVideo: Boolean = elem.size > 0
            var videoUri: Uri? = null
            if (hasVideo) {
                val jsSrc = elem.attr("src")

                var video: HtmlVideo? = null
                val webClient = WebClient(BrowserVersion.CHROME)
                var done = false
                val page: HtmlPage = webClient.getPage(jsSrc)
                do {
                    Thread.sleep(100)
                    try {
                        video = page.getHtmlElementById("dhy-video")
                        done = true
                    } catch (_: ElementNotFoundException) {
                    }
                } while (!done)

                videoUri = Uri.parse(video?.src)
                Log.d("video uri", videoUri.toString())
            }

            runOnUiThread {
                if (hasVideo) {
                    show(videoUri!!)
                } else {
                    videoView.visibility = GONE
                    seekBar.visibility = GONE
                }
                text(title, paragraph)
            }
        }.start()

    private fun show(uri: Uri) {
        Log.d("video view", "show invoked")
        videoView.setVideoURI(uri)
        videoView.start()
        handler.postDelayed(update, 100)
    }

    private fun text(title: String, body: String) {
        viewBinding.titleTextView.text = title
        viewBinding.bodyTextView.text = body
    }
}