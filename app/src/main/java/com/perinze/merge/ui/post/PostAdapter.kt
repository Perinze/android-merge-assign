package com.perinze.merge.ui.post

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.perinze.merge.AppDatabase
import com.perinze.merge.R

class PostAdapter(private val context: Context, lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<Post>>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val db = AppDatabase.getInstance(context).postDao()

    init {
        liveData.observe(lifecycleOwner) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false)
        return PostHolder(itemView)
    }

    override fun getItemCount(): Int {
        return liveData.value!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val postHolder = holder as PostHolder
        val post = liveData.value!![position]
        postHolder.textView.text = post.title
        postHolder.itemView.setOnClickListener {
            Toast.makeText(context, "implement post viewer", Toast.LENGTH_SHORT).show()
        }
    }

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.post_title)
    }
}