package com.perinze.merge.ui.edit

import android.os.Bundle
import android.view.Menu
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.perinze.merge.R
import com.perinze.merge.databinding.ActivityEditBinding
import com.perinze.merge.ui.post.PostAdapter
import com.perinze.merge.ui.post.PostViewModel
import com.perinze.merge.ui.post.PostViewModelFactory

class EditActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //postViewModel = ViewModelProvider(this, PostViewModelFactory(this))[PostViewModel::class.java]
        //postViewModel.retrieve()

        viewBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //val toolbar = viewBinding.toolbar
        //toolbar.inflateMenu(R.menu.edit_menu)
        setSupportActionBar(viewBinding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}