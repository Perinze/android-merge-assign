package com.perinze.merge.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.perinze.merge.AppDatabase
import com.perinze.merge.R
import com.perinze.merge.ui.favorite.Favorite

class SearchResultAdapter(private val context: Context, lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<SearchResult>>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val db = AppDatabase.getInstance(context).favoriteDao()

    init {
        liveData.observe(lifecycleOwner) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.search_result_item, parent, false)
        return SearchResultHolder(itemView)
    }

    override fun getItemCount(): Int {
        return liveData.value!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val searchResultHolder = holder as SearchResultHolder
        val searchResult = liveData.value!![position]
        searchResultHolder.titleView.text = searchResult.title
        searchResultHolder.previewView.text = searchResult.preview
        searchResultHolder.groupView.text = searchResult.group
        searchResultHolder.dateView.text = searchResult.date
        searchResultHolder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchResult.link))
            context.startActivity(intent)
        }
        searchResultHolder.itemView.setOnLongClickListener {
            val dbEntity: List<Favorite> = db.getAllByUrl(searchResult.link)
            if (dbEntity.isEmpty()) {
                db.insertAll(Favorite(0, searchResult.title, searchResult.link))
                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show()
            } else {
                db.deleteById(dbEntity[0].id)
                Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    class SearchResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.search_result_item_title)
        val previewView: TextView = itemView.findViewById(R.id.search_result_item_preview)
        val groupView: TextView = itemView.findViewById(R.id.search_result_item_group)
        val dateView: TextView = itemView.findViewById(R.id.search_result_item_date)
    }
}