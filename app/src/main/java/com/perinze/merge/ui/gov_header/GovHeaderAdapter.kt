package com.perinze.merge.ui.gov_header

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

class GovHeaderAdapter(private val context: Context, lifecycleOwner: LifecycleOwner, private val liveData: LiveData<List<GovHeader>>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        govHeaderHolder.imageView.load(liveData.value!![position].img)
        govHeaderHolder.textView.text = liveData.value!![position].title
        govHeaderHolder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(liveData.value!![position].url))
            context.startActivity(intent)
        }
    }

    class GovHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.govHeaderItemImageView)
        val textView: TextView = itemView.findViewById(R.id.govHeaderItemTextView)
    }
}