package com.perinze.merge.ui.edit

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.Menu
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.perinze.merge.AppDatabase
import com.perinze.merge.R
import com.perinze.merge.databinding.ActivityEditBinding
import com.perinze.merge.ui.post.Post
import com.perinze.merge.ui.post.PostAdapter
import com.perinze.merge.ui.post.PostDao
import com.perinze.merge.ui.post.PostViewModel
import com.perinze.merge.ui.post.PostViewModelFactory

class EditActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityEditBinding
    private lateinit var db: PostDao
    private var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setSupportActionBar(viewBinding.toolbar)

        db = AppDatabase.getInstance(this).postDao()

        var id: Int? = null
        if (intent.hasExtra("id")) {
            id = intent.getIntExtra("id", 0)
        } else if (intent.hasExtra("name")) {
            val post = Post(0, intent.getStringExtra("name"), "")
            id = db.insertAll(post)[0].toInt()
            Log.d("new id", id.toString())
        } else {
            finish()
        }
        post = db.getAllById(id!!)[0]

        viewBinding.codeEditText.text = SpannableStringBuilder(post?.body)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        menu.findItem(R.id.menu_save).setOnMenuItemClickListener {
            post!!.body = viewBinding.codeEditText.text.toString()
            db.update(post!!)
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
            true
        }
        return super.onCreateOptionsMenu(menu)
    }
}