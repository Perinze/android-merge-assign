package com.perinze.merge.ui.favorite

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.perinze.merge.R

class FavoriteAdapter(private val context: Context, lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<Favorite>>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val db = AppDatabase.getInstance(context).favoriteDao()

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
        val favorite = liveData.value!![position]
        favoriteHolder.textView.text = favorite.title
        favoriteHolder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(favorite.url))
            context.startActivity(intent)
        }
        if (db.getAllById(favorite.id).isNotEmpty()) {
            favoriteHolder.mark.isChecked = true
        }
        favoriteHolder.mark.setOnCheckedChangeListener { _, b ->
            if (b) {
                db.insertAll(favorite)
            } else {
                db.deleteById(favorite.id)
            }
        }
    }

    class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.favorite_title)
        val mark: CheckBox = itemView.findViewById(R.id.mark)
    }
}