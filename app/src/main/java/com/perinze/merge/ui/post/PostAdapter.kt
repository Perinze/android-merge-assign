package com.perinze.merge.ui.post

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.perinze.merge.AppDatabase
import com.perinze.merge.R
import com.perinze.merge.ui.edit.EditActivity

class PostAdapter(private val context: Context, lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<Post>>, private val viewModel: PostViewModel):
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
            val intent = Intent(context, RenderActivity::class.java)
            intent.putExtra("code", post.body)
            context.startActivity(intent)
        }

        val popupMenu = PopupMenu(context, postHolder.itemView)
        popupMenu.inflate(R.menu.post_popup_menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_edit -> {
                    val intent = Intent(context, EditActivity::class.java)
                    intent.putExtra("id", post.id)
                    context.startActivity(intent)
                    true
                }
                R.id.menu_delete -> {
                    viewModel.delete(post.id)
                    true
                }
                else -> {
                    true
                }
            }
        }

        postHolder.itemView.setOnLongClickListener {
            popupMenu.show()
            true
        }
    }

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.post_title)
    }
}