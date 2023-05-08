package com.perinze.merge.ui.favorite

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
import com.perinze.merge.ui.article.ArticleActivity

class FavoriteAdapter(private val context: Context, lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<Favorite>>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val db = AppDatabase.getInstance(context).favoriteDao()

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
            val articleIntent = Intent(context, ArticleActivity::class.java)
            articleIntent.putExtra("url", favorite.url)
            context.startActivity(articleIntent)
        }
        if (db.getAllById(favorite.id).isNotEmpty()) {
            favoriteHolder.mark.isChecked = true
        }
        favoriteHolder.mark.setOnCheckedChangeListener { _, b ->
            if (b) {
                db.insertAll(favorite)
                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show()
            } else {
                db.deleteById(favorite.id)
                Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.favorite_title)
        val mark: CheckBox = itemView.findViewById(R.id.mark)
    }
}