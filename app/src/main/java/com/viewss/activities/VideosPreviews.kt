package com.viewss.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.devatrii.statussaver.utils.Constants
import com.example.statussaver.R
import com.example.statussaver.databinding.ActivityVideosPreviewsBinding
import com.modelss.MediaModels
import com.viewss.adapters.ImagePreviewAdapters
import com.viewss.adapters.VideoPreviewAdapters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideosPreviews : AppCompatActivity() {
    private val activity = this
    private val binding by lazy {
        ActivityVideosPreviewsBinding.inflate(layoutInflater)
    }
    lateinit var adapters:VideoPreviewAdapters
    private val TAG = "VideosPreview"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            val list = intent.getSerializableExtra(Constants.MEDIA_LIST_KEY) as ArrayList<MediaModels>
            val scrollTo = intent.getIntExtra(Constants.MEDIA_LIST_KEY, 0)
            adapters = VideoPreviewAdapters(list, activity)
            videoRecyclerView.adapter = adapters

            val pageSnapHelper = PagerSnapHelper()
            pageSnapHelper.attachToRecyclerView(videoRecyclerView)
            videoRecyclerView.scrollToPosition(scrollTo)


            videoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        Log.d(TAG, "onScrollStateChanged: Dragging")
                        stopAllPlayers()
                    }
                }


            })




        }
    }



    private fun stopAllPlayers() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                binding.apply {
                    for (i in 0 until videoRecyclerView.childCount) {
                        val child = videoRecyclerView.getChildAt(i)
                        val viewHolder = videoRecyclerView.getChildViewHolder(child)
                        if (viewHolder is VideoPreviewAdapters.ViewHolder) {
                            viewHolder.stopPlayer()
                        }
                    }
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()
        stopAllPlayers()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAllPlayers()
    }

}