package com.perinze.merge.ui.favorite

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.perinze.merge.R

class FavoriteAdapter(private val context: Context, lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<Favorite>>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        liveData.observe(lifecycleOwner) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false)
        return FavoriteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return liveData.value!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val favoriteHolder = holder as FavoriteHolder
        favoriteHolder.textView.text = liveData.value!![position].title
        favoriteHolder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(liveData.value!![position].url))
            context.startActivity(intent)
        }
    }

    class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.favorite_title)
    }
}