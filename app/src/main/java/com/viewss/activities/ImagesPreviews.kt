package com.viewss.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.devatrii.statussaver.utils.Constants
import com.example.statussaver.R
import com.example.statussaver.databinding.ActivityImagesPreviewsBinding
import com.modelss.MediaModels
import com.viewss.adapters.ImagePreviewAdapters

class ImagesPreviews : AppCompatActivity() {
    private val activity = this
    private val binding by lazy {
        ActivityImagesPreviewsBinding.inflate(layoutInflater)
    }
    lateinit var  adapters:ImagePreviewAdapters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
          val list = intent.getSerializableExtra(Constants.MEDIA_LIST_KEY) as ArrayList<MediaModels>
            val scrollTo = intent.getIntExtra(Constants.MEDIA_LIST_KEY, 0)
            adapters = ImagePreviewAdapters(list, activity)
            imagesViewPager.adapter = adapters
            imagesViewPager.currentItem = scrollTo

        }
    }
}