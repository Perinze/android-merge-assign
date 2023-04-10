package com.perinze.merge.ui.meeting_header

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

class MeetingHeaderAdapter(private val context: Context, private val lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<MeetingHeader>>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        meetingHeaderHolder.imageView.load(liveData.value!![position].img)
        meetingHeaderHolder.textView.text = liveData.value!![position].title
        meetingHeaderHolder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(liveData.value!![position].url))
            context.startActivity(intent)
        }
    }

    class MeetingHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.meetingHeaderItemImageView)
        val textView: TextView = itemView.findViewById(R.id.meetingHeaderItemTextView)
    }
}