package com.perinze.merge.ui.gov_header

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.perinze.merge.R
import com.perinze.merge.AppDatabase
import com.perinze.merge.ui.favorite.Favorite

class GovHeaderAdapter(private val context: Context, lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<GovHeader>>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val db = AppDatabase.getInstance(context).favoriteDao()

    init {
        liveData.observe(lifecycleOwner) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.gov_header_item, parent, false)
        return GovHeaderHolder(itemView)
    }

    override fun getItemCount(): Int {
        return liveData.value!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val govHeaderHolder = holder as GovHeaderHolder
        val govHeader = liveData.value!![position]
        govHeaderHolder.imageView.load(govHeader.img)
        govHeaderHolder.textView.text = govHeader.title
        govHeaderHolder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(govHeader.url))
            context.startActivity(intent)
        }
        govHeaderHolder.itemView.setOnLongClickListener {
            val dbEntity: List<Favorite> = db.getAllByUrl(govHeader.url)
            if (dbEntity.isEmpty()) {
                db.insertAll(Favorite(0, govHeader.title, govHeader.url))
                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show()
            } else {
                db.deleteById(dbEntity[0].id)
                Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    class GovHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.govHeaderItemImageView)
        val textView: TextView = itemView.findViewById(R.id.govHeaderItemTextView)
    }
}