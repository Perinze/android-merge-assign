package com.perinze.merge.ui.edit

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setSupportActionBar(viewBinding.toolbar)

        db = AppDatabase.getInstance(this).postDao()

        if (intent.hasExtra("name")) {
            db.insertAll(Post(0, intent.getStringExtra("name"), ""))
            Log.d("edit activity", db.getAll().toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        menu.findItem(R.id.menu_save).setOnMenuItemClickListener {
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
            true
        }
        return super.onCreateOptionsMenu(menu)
    }
}