package com.perinze.merge.ui.post

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perinze.merge.databinding.ActivityPostBinding
import com.perinze.merge.ui.edit.EditActivity
import com.perinze.merge.ui.favorite.FavoriteAdapter

class PostActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityPostBinding

    private lateinit var postViewModel: PostViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postViewModel = ViewModelProvider(this, PostViewModelFactory(this))[PostViewModel::class.java]
        postViewModel.retrieve()

        viewBinding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        recyclerView = viewBinding.postRecyclerView

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.adapter = PostAdapter(this, this, postViewModel.result)

        viewBinding.postFab.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }
}