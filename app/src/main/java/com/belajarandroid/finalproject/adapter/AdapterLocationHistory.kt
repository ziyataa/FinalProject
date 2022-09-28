package com.belajarandroid.finalproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.model.ResultItemHistory
import com.bumptech.glide.Glide

class AdapterLocationHistory(private var listItem: ArrayList<ResultItemHistory>) :
    RecyclerView.Adapter<LocationHistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_location, parent, false)
        return LocationHistoryViewHolder(view)

    }

    override fun onBindViewHolder(holder: LocationHistoryViewHolder, position: Int) {
        val pagerItem = listItem[position]
        holder.bind(pagerItem)

    }

    override fun getItemCount(): Int {
        return listItem.size
    }

}

class LocationHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val locationImage = itemView.findViewById<ImageView>(R.id.imgListHome)
    val locationTitle = itemView.findViewById<TextView>(R.id.txtListTitleHome)
    val locationDesc = itemView.findViewById<TextView>(R.id.txtListDescHome)


    fun bind(model: ResultItemHistory) {
        Glide.with(itemView.context).load(model.path).into(locationImage)
        locationTitle.text = model.name
        locationDesc.text = model.addressLocation
    }
}