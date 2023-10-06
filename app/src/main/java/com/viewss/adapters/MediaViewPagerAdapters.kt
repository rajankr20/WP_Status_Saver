package com.viewss.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devatrii.statussaver.utils.Constants
import com.viewss.fragmentss.FragmentMedias

class MediaViewPagerAdapters (
    private val fragmentActivity: FragmentActivity,
    private val imageType:String = Constants.MEDIA_TYPE_WHATSAPP_IMAGES,
    private val videosType:String = Constants.MEDIA_TYPE_WHATSAPP_VIDEOS,
) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount()= 2

    override fun createFragment(position: Int): Fragment {
        return  when(position){
            0->{
                // images media fragment
                val mediaFragment = FragmentMedias()
                val bundle = Bundle()
                bundle.putString(Constants.MEDIA_LIST_KEY, imageType)
                mediaFragment.arguments = bundle
                mediaFragment
            }else->{
                // video media fragemnt
                val mediaFragment = FragmentMedias()
                val bundle = Bundle()
                bundle.putString(Constants.MEDIA_LIST_KEY, videosType)
                mediaFragment.arguments = bundle
                mediaFragment
            }
        }
    }


}