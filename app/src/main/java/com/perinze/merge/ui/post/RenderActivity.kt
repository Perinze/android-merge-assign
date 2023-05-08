package com.perinze.merge.ui.post

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.Html.ImageGetter
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ImageSpan
import android.text.style.URLSpan
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import coil.imageLoader
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.commit451.coilimagegetter.CoilImageGetter
import com.google.mlkit.vision.common.internal.ImageUtils
import com.perinze.merge.R
import com.perinze.merge.databinding.ActivityRenderBinding

class RenderActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityRenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityRenderBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if (!intent.hasExtra("code")) finish()
        val code = intent.getStringExtra("code")!!
        Log.d("code", code)
        val lines = code.split("\n")

        var html = ""
        for (line in lines) {
            val words = line.split(" ")
            val params = words.subList(1, words.lastIndex + 1)
            when (words[0]) {
                "text" -> {
                    html += "<p>${params.joinToString(" ")}</p>\n"
                }
                "img" -> {
                    html += "<p><img src=\"${params[0]}\"></p>\n"
                }
                "url" -> {
                    html += "<p><a href=\"${params[0]}\">${params[0]}</a></p>\n"
                }
                else -> {}
            }
        }
        Log.d("html", html)

        viewBinding.renderTextView.text = Html.fromHtml(
            html,
            CoilImageGetter(viewBinding.renderTextView),
            null
        )
    }
}