package com.perinze.merge.ui.meeting_header

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
import com.perinze.merge.ui.favorite.AppDatabase
import com.perinze.merge.ui.favorite.Favorite

class MeetingHeaderAdapter(private val context: Context, private val lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<MeetingHeader>>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val db = AppDatabase.getInstance(context).favoriteDao()

    init {
        liveData.observe(lifecycleOwner) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.meeting_header_item, parent, false)
        return MeetingHeaderHolder(itemView)
    }

    override fun getItemCount(): Int {
        return liveData.value!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val meetingHeaderHolder = holder as MeetingHeaderHolder
        val meetingHeader = liveData.value!![position]
        meetingHeaderHolder.imageView.load(meetingHeader.img)
        meetingHeaderHolder.textView.text = meetingHeader.title
        meetingHeaderHolder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meetingHeader.url))
            context.startActivity(intent)
        }
        meetingHeaderHolder.itemView.setOnLongClickListener {
            val dbEntity: List<Favorite> = db.getAllByUrl(meetingHeader.url)
            if (dbEntity.isEmpty()) {
                db.insertAll(Favorite(0, meetingHeader.title, meetingHeader.url))
                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show()
            } else {
                db.deleteById(dbEntity[0].id)
                Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    class MeetingHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.meetingHeaderItemImageView)
        val textView: TextView = itemView.findViewById(R.id.meetingHeaderItemTextView)
    }
}