package com.belajarandroid.finalproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.model.ResultItem
import com.bumptech.glide.Glide

class ViewPagerAdapter( private var listItem : ArrayList<ResultItem> )
    : RecyclerView.Adapter<PagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return PagerViewHolder(view)

    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val pagerItem = listItem[position]
        holder.bind(pagerItem)

    }

    override fun getItemCount(): Int {
        return listItem.size
    }

}

class  PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val pagerImage = itemView.findViewById<ImageView>(R.id.item_image)
    val pagerTitle = itemView.findViewById<TextView>(R.id.item_title)
    val pagerDesc = itemView.findViewById<TextView>(R.id.item_desc)

    fun bind(model: ResultItem) {
        Glide.with(itemView.context).load(model.path).into(pagerImage)
        pagerTitle.text = model.title
        pagerDesc.text = model.description
    }
}