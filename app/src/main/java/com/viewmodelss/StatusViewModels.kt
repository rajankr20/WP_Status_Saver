package com.viewmodelss

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.datas.StatusRepos
import com.devatrii.statussaver.utils.Constants
import com.devatrii.statussaver.utils.SharedPrefKeys
import com.devatrii.statussaver.utils.SharedPrefUtils
import com.modelss.MEDIA_TYPE_IMAGE
import com.modelss.MEDIA_TYPE_VIDEO
import com.modelss.MediaModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatusViewModels(val repos: StatusRepos):ViewModel() {

    private val wpStatusLiveData get()= repos.whatsAppStatusLiveData
    private val wpBusinessStatusLiveData get() = repos.whatsAppBusinessStatusLiveData

    //wp main
    val whatsAppImagesLiveData = MutableLiveData<ArrayList<MediaModels>>()
    val whatsAppVideosLiveData = MutableLiveData<ArrayList<MediaModels>>()

    //wp business
    val whatsAppBusinessImagesLiveData = MutableLiveData<ArrayList<MediaModels>>()
    val whatsAppBusinessVideosLiveData = MutableLiveData<ArrayList<MediaModels>>()

    private var isPermissionsGranted = false

    init {
        SharedPrefUtils.init(repos.context)

        val wpPermission = SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, false)
        val wpBusinessPermission = SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED, false)

        isPermissionsGranted  = wpPermission && wpBusinessPermission
        if (isPermissionsGranted){
            CoroutineScope(Dispatchers.IO).launch {
                repos.getAllStatuses()

            }
            CoroutineScope(Dispatchers.IO).launch {

                repos.getAllStatuses(Constants.TYPE_WHATSAPP_MAIN)
            }
        }
    }


    fun getWhatsAppStatuses(){
        CoroutineScope(Dispatchers.IO).launch {
          if (!isPermissionsGranted){
              repos.getAllStatuses()
          }

            withContext(Dispatchers.Main){
                getWhatsAppImages()
                getWhatsAppVideos()
            }
        }
    }

    fun getWhatsAppImages(){
        wpStatusLiveData.observe(repos.activity as LifecycleOwner){
            val  tempList = ArrayList<MediaModels>()
            it.forEach { mediaModels ->
                if (mediaModels.type == MEDIA_TYPE_IMAGE){
                    tempList.add(mediaModels)
                }
            }
            whatsAppImagesLiveData.postValue(tempList)
        }
    }

    fun getWhatsAppVideos(){
        wpStatusLiveData.observe(repos.activity as LifecycleOwner){
            val  tempList = ArrayList<MediaModels>()
            it.forEach { mediaModels ->
                if (mediaModels.type == MEDIA_TYPE_VIDEO){
                    tempList.add(mediaModels)
                }
            }
            whatsAppVideosLiveData.postValue(tempList)
        }
    }






    fun getWhatsAppBusinessStatuses(){
        CoroutineScope(Dispatchers.IO).launch {
            if (!isPermissionsGranted){
                repos.getAllStatuses(Constants.TYPE_WHATSAPP_BUSINESS)
            }

            withContext(Dispatchers.Main){
                getWhatsAppBusinessImages()
                getWhatsAppBusinessVideos()
            }
        }
    }

    fun getWhatsAppBusinessImages(){
        wpBusinessStatusLiveData.observe(repos.activity as LifecycleOwner){
            val  tempList = ArrayList<MediaModels>()
            it.forEach { mediaModels ->
                if (mediaModels.type == MEDIA_TYPE_IMAGE){
                    tempList.add(mediaModels)
                }
            }
            whatsAppBusinessImagesLiveData.postValue(tempList)
        }
    }

    fun getWhatsAppBusinessVideos(){
        wpBusinessStatusLiveData.observe(repos.activity as LifecycleOwner){
            val  tempList = ArrayList<MediaModels>()
            it.forEach { mediaModels ->
                if (mediaModels.type == MEDIA_TYPE_VIDEO){
                    tempList.add(mediaModels)
                }
            }
            whatsAppBusinessImagesLiveData.postValue(tempList)
        }
    }

}