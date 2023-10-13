package com.datas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import com.devatrii.statussaver.utils.Constants
import com.devatrii.statussaver.utils.SharedPrefKeys
import com.devatrii.statussaver.utils.SharedPrefUtils
import com.modelss.MEDIA_TYPE_IMAGE
import com.modelss.MEDIA_TYPE_VIDEO
import com.modelss.MediaModels
import com.utilss.getFileExtension
import com.utilss.isStatusExist

class StatusRepos(val context:Context) {


    val whatsAppStatusLiveData = MutableLiveData<ArrayList<MediaModels>>()
    val whatsAppBusinessStatusLiveData = MutableLiveData<ArrayList<MediaModels>>()

    val activity = context as Activity

    private val wpStatusesList = ArrayList<MediaModels>()
    private val wpBusinessStatusesList = ArrayList<MediaModels>()

    private val TAG = "StatusRepos"
    fun getAllStatuses(whatsAppType: String = Constants.TYPE_WHATSAPP_MAIN) {
        val treeUri = when (whatsAppType) {
            Constants.TYPE_WHATSAPP_MAIN -> {
                SharedPrefUtils.getPrefString(SharedPrefKeys.PREF_KEY_WP_TREE_URI, "")?.toUri()!!
            }

            else -> {
                SharedPrefUtils.getPrefString(SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI, "")
                    ?.toUri()!!
            }
        }
        Log.d(TAG, "getAllStatuses: $treeUri")

        activity.contentResolver.takePersistableUriPermission(
            treeUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        val fileDocument = DocumentFile.fromTreeUri(activity, treeUri)

        fileDocument?.let {
            it.listFiles().forEach { file ->
                Log.d(TAG,"getAllStatuses:${file.name}")
                if (file.name != ".nomedia" && file.isFile) {
                    val isDownloaded = context.isStatusExist(file.name!!)
                    Log.d(TAG,"getAllStatuses: Extension: ${getFileExtension((file.name!!))} || ${file.name}")

                    val type = if (getFileExtension(file.name!!) == "mp4") {
                        MEDIA_TYPE_VIDEO
                    } else {
                        MEDIA_TYPE_IMAGE
                    }

                    val models = MediaModels(
                        pathUri = file.uri.toString(),
                        fileName = file.name!!,
                        type = type,
                        isDownloaded = isDownloaded
                    )
                    when (whatsAppType) {
                        Constants.TYPE_WHATSAPP_MAIN -> {
                            wpStatusesList.add(models)
                        }

                        else -> {
                            wpBusinessStatusesList.add(models)
                        }
                    }

                }

            }


            when (whatsAppType) {
                Constants.TYPE_WHATSAPP_MAIN -> {
                    Log.d(TAG,"getAllStatuses: pushing Value to wp live Data")
                    whatsAppStatusLiveData.postValue(wpStatusesList)
                }

                else -> {
                    Log.d(TAG,"getAllStatuses: pushing Value to Wp Business live Data")
                    whatsAppBusinessStatusLiveData.postValue(wpBusinessStatusesList)
                }

            }
        }
    }
}