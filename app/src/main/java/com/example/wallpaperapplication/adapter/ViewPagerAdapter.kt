package com.example.wallpaperapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.wallpaperapplication.R
import com.example.wallpaperapplication.data.HitsItem
import com.example.wallpaperapplication.databinding.ViewPagerLayoutBinding

class ViewPagerAdapter(private var wallList:MutableList<HitsItem?>) : Adapter<ViewPagerAdapter.PagerViewHolder>() {

    class PagerViewHolder(itemView: View) : ViewHolder(itemView){
        val binding = ViewPagerLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_layout,parent,false)
        return PagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wallList.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(wallList[position]!!.largeImageURL)
            .placeholder(R.drawable.wallback2).into(holder.binding.pagerSetImage)
    }
}