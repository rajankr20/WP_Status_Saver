package com.viewss.adapters

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devatrii.statussaver.utils.Constants
import com.example.statussaver.R
import com.example.statussaver.R.*
import com.example.statussaver.databinding.ItemMediaBinding
import com.modelss.MEDIA_TYPE_IMAGE
import com.modelss.MediaModels
import com.utilss.saveStatus
import com.viewss.activities.ImagesPreviews
import com.viewss.activities.VideosPreviews

class MediaAdapters (val list: ArrayList<MediaModels>, val context: Context):RecyclerView.Adapter<MediaAdapters.ViewHolder>(){

    inner class ViewHolder(val binding:ItemMediaBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(mediaModels: MediaModels){
            binding.apply {

                Glide.with(context).load(mediaModels.pathUri.toUri()).into(statusImage)
                if (mediaModels.type == MEDIA_TYPE_IMAGE){
                    statusPlay.visibility = View.GONE
                }

                val downloadImage = if (mediaModels.isDownloaded){
                     R.drawable.ic_downloadeds
                }else{
                    R.drawable.ic_downloads
                }
                statusDownload.setImageResource(downloadImage)

                cardStatus.setOnClickListener {
                    if (mediaModels.type == MEDIA_TYPE_IMAGE){
                        // go image preview activity
                        Intent().apply {
                            putExtra(Constants.MEDIA_LIST_KEY, list)
                            putExtra(Constants.MEDIA_LIST_KEY, layoutPosition)
                            setClass(context, ImagesPreviews::class.java)
                            context.startActivity(this)
                        }
                    }else{
                        // go to video preview activity
                        Intent().apply {
                            putExtra(Constants.MEDIA_LIST_KEY, list)
                            putExtra(Constants.MEDIA_LIST_KEY, layoutPosition)
                            setClass(context, VideosPreviews::class.java)
                            context.startActivity(this)
                        }
                    }
                }

                statusDownload.setOnClickListener {
                    val isDownloaded = context.saveStatus(mediaModels)
                    if (isDownloaded){
                        //status image preview activitiy
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        mediaModels.isDownloaded = true
                        statusDownload.setImageResource(R.drawable.ic_downloads)
                    }else{
                        //unable to download status
                        Toast.makeText(context, "Unable to save", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         return ViewHolder(ItemMediaBinding.inflate(LayoutInflater.from(context),parent, false))
    }

    override fun getItemCount()= list.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val model = list[position]
        holder.bind(model)
    }
}