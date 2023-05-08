package com.perinze.merge.ui.post

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perinze.merge.R
import com.perinze.merge.databinding.ActivityPostBinding
import com.perinze.merge.ui.edit.EditActivity

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

        recyclerView.adapter = PostAdapter(this, this, postViewModel.result, postViewModel)

        viewBinding.postFab.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("新笔记")

            val view = layoutInflater.inflate(R.layout.dialog_post_title, null)
            val editText: EditText = view.findViewById(R.id.dialog_post_title_edit)
            alert.setView(view)

            alert.setPositiveButton("确定") { _, _ ->
                val name = editText.text.toString()
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
            alert.setNegativeButton("取消") { dialog, _ ->
                dialog.cancel()
            }
            alert.show()
        }
    }
}