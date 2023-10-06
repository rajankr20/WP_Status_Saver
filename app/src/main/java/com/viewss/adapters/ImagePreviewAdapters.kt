package com.viewss.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.statussaver.R
import com.example.statussaver.databinding.ItemImagePreviewsBinding
import com.example.statussaver.databinding.ItemMediaBinding
import com.modelss.MEDIA_TYPE_IMAGE
import com.modelss.MediaModels
import com.utilss.saveStatus

class ImagePreviewAdapters(val list: ArrayList<MediaModels>, val context: Context):
    RecyclerView.Adapter<ImagePreviewAdapters.ViewHolder>() {

    inner class ViewHolder(val binding: ItemImagePreviewsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(mediaModels: MediaModels){
            binding.apply {

                Glide.with(context).load(mediaModels.pathUri.toUri()).into(zoomableImageView)


                val downloadImage = if (mediaModels.isDownloaded){
                    R.drawable.ic_downloadeds
                }else{
                    R.drawable.ic_downloads
                }
                tools.statusDownload.setImageResource(downloadImage)



                tools.download.setOnClickListener {
                    val isDownloaded = context.saveStatus(mediaModels)
                    if (isDownloaded){
                        //status image preview activitiy
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        mediaModels.isDownloaded = true
                        tools.statusDownload.setImageResource(R.drawable.ic_downloads)
                    }else{
                        //unable to download status
                        Toast.makeText(context, "Unable to save", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagePreviewAdapters.ViewHolder {
        return ViewHolder(ItemImagePreviewsBinding.inflate(LayoutInflater.from(context),parent, false))
    }

    override fun onBindViewHolder(holder: ImagePreviewAdapters.ViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model)
    }

    override fun getItemCount() = list.size


}