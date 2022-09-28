package com.belajarandroid.finalproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.model.ResultItemLocation
import com.bumptech.glide.Glide

class AdapterLocation(private var listItem: ArrayList<ResultItemLocation>,
                      private var onItemSelectedListener : ((ResultItemLocation) -> Unit)? = null) :
    RecyclerView.Adapter<AdapterLocation.LocationViewHolder>() {

//    fun onItemSelected(listener: ((ResultItemLocation) -> Unit)?){
//        this.onItemSelectedListener = listener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val pagerItem = listItem[position]
        holder.bind(pagerItem)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locationImage = itemView.findViewById<ImageView>(R.id.imgListHome)
        val locationTitle = itemView.findViewById<TextView>(R.id.txtListTitleHome)
        val locationDesc = itemView.findViewById<TextView>(R.id.txtListDescHome)


        fun bind(model: ResultItemLocation) {
            Glide.with(itemView.context).load(model.path).into(locationImage)
            locationTitle.text = model.name
            locationDesc.text = model.address
            if (model.isSelected){
//            locationImage.setBackgroundResource(R.drawable.bg_selected)
                itemView.background = ContextCompat.getDrawable(itemView.context, R.color.bilu)
            } else {
                itemView.background = null
            }
            itemView.setOnClickListener {
                onItemSelectedListener?.invoke(model)
            }
        }
    }
}