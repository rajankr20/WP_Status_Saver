package com.viewss.adapters

//import android.content.Context
//import android.media.browse.MediaBrowser.MediaItem
//import android.net.Uri
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.net.toUri
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.statussaver.R
//import com.example.statussaver.databinding.ItemImagePreviewsBinding
//import com.example.statussaver.databinding.ItemMediaBinding
//import com.example.statussaver.databinding.ItemVideoPreviewBinding
//import com.modelss.MEDIA_TYPE_IMAGE
//import com.modelss.MediaModels
//import com.utilss.saveStatus
//
//class VideoPreviewAdapters(val list: ArrayList<MediaModels>, val context: Context):
//    RecyclerView.Adapter<VideoPreviewAdapters.ViewHolder>() {
//
//    inner class ViewHolder(val binding: ItemVideoPreviewBinding):RecyclerView.ViewHolder(binding.root){
//        fun bind(mediaModels: MediaModels){
//            binding.apply {
//
//                val player = ExoPlayer.Builder(context).build()
//                playerView.player = player
//
//
//                val mediaItem = MediaItem.Builder()
//                    .setUri(Uri.parse(mediaModels.pathUri))
//                    .build()
//
//                player.setMediaItem(mediaItem)
//                player.prepare()
//                player.prepare()
//
//
//                val downloadImage = if (mediaModels.isDownloaded){
//                    R.drawable.ic_downloadeds
//                }else{
//                    R.drawable.ic_downloads
//                }
//                tools.statusDownload.setImageResource(downloadImage)
//
//
//
//                tools.download.setOnClickListener {
//                    val isDownloaded = context.saveStatus(mediaModels)
//                    if (isDownloaded){
//                        //status image preview activitiy
//                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
//                        mediaModels.isDownloaded = true
//                        tools.statusDownload.setImageResource(R.drawable.ic_downloads)
//                    }else{
//                        //unable to download status
//                        Toast.makeText(context, "Unable to save", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
//    }
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): VideoPreviewAdapters.ViewHolder {
//        return ViewHolder(ItemVideoPreviewBinding.inflate(LayoutInflater.from(context),parent, false))
//    }
//
//    override fun onBindViewHolder(holder: VideoPreviewAdapters.ViewHolder, position: Int) {
//        val model = list[position]
//        holder.bind(model)
//    }
//
//    override fun getItemCount() = list.size
//
//
//}

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.example.statussaver.R
import com.example.statussaver.databinding.ItemVideoPreviewBinding
import com.modelss.MediaModels
import com.utilss.saveStatus

class VideoPreviewAdapters(val list: ArrayList<MediaModels>, val context: Context) :
    RecyclerView.Adapter<VideoPreviewAdapters.ViewHolder>() {

    inner class ViewHolder(val binding: ItemVideoPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaModel: MediaModels) {
            binding.apply {

                val player = ExoPlayer.Builder(context).build()
                playerView.player = player
                val mediaItem = MediaItem.fromUri(mediaModel.pathUri)

                player.setMediaItem(mediaItem)

                player.prepare()


                val downloadImage = if (mediaModel.isDownloaded) {
                    R.drawable.ic_downloadeds
                } else {
                    R.drawable.ic_downloads
                }
                tools.statusDownload.setImageResource(downloadImage)



                tools.download.setOnClickListener {
                    val isDownloaded = context.saveStatus(mediaModel)
                    if (isDownloaded) {
                        // status is downloaded
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        mediaModel.isDownloaded = true
                        tools.statusDownload.setImageResource(R.drawable.ic_downloadeds)
                    } else {
                        // unable to download status
                        Toast.makeText(context, "Unable to Save", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        fun stopPlayer(){
            binding.playerView.player?.stop()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoPreviewAdapters.ViewHolder {
        return ViewHolder(
            ItemVideoPreviewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoPreviewAdapters.ViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model)
    }

    override fun getItemCount() = list.size

}