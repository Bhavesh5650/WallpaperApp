package com.example.wallpaperapplication.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.wallpaperapplication.R
import com.example.wallpaperapplication.activity.WallpaperShowActivity
import com.example.wallpaperapplication.data.HitsItem
import com.example.wallpaperapplication.databinding.WallpaperSampleLayoutBinding

class WallpaperAdapter(private var wallList:MutableList<HitsItem?>) : Adapter<WallpaperAdapter.WallViewHolder>() {

    class WallViewHolder(itemView: View) : ViewHolder(itemView)
    {
        val binding = WallpaperSampleLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_sample_layout,parent,false)
        return WallViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wallList.size
    }

    override fun onBindViewHolder(holder: WallViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(wallList[position]!!.previewURL)
            .placeholder(R.drawable.wallback)
            .into(holder.binding.setWallpaperImage)
        holder.binding.cardBackground.setOnClickListener {
            val intent = Intent(holder.itemView.context,WallpaperShowActivity::class.java)
            intent.putExtra("wallpaper",wallList[position]!!.largeImageURL)
            intent.putExtra("position",position)
            holder.itemView.context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<HitsItem?>)
    {
        wallList.addAll(list)
        notifyDataSetChanged()
    }
    fun clearData()
    {
        wallList.clear()
    }


}