package com.perinze.merge.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.perinze.merge.R
import com.perinze.merge.ui.favorite.AppDatabase
import com.perinze.merge.ui.favorite.Favorite

class SearchResultAdapter(private val context: Context, lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<SearchResult>>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        searchResultHolder.titleView.text = liveData.value!![position].title
        searchResultHolder.previewView.text = liveData.value!![position].preview
        searchResultHolder.groupView.text = liveData.value!![position].group
        searchResultHolder.dateView.text = liveData.value!![position].date
        searchResultHolder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(liveData.value!![position].link))
            context.startActivity(intent)
        }
        searchResultHolder.itemView.setOnLongClickListener {
            AppDatabase.getInstance(context).favoriteDao()
                .insertAll(Favorite(0, liveData.value!![position].title, liveData.value!![position].link))
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