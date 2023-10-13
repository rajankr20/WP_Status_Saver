package com.viewss.fragmentss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.datas.StatusRepos
import com.devatrii.statussaver.utils.Constants
import com.example.statussaver.databinding.FragmentMediasBinding
import com.modelss.MediaModels
import com.viewmodelss.StatusViewModels
import com.viewmodelss.factoriess.StatusViewModelFactorys
import com.viewss.adapters.MediaAdapters


class FragmentMedias : Fragment() {

      private val binding by lazy {
          FragmentMediasBinding.inflate(layoutInflater)
      }
    lateinit var  viewModels: StatusViewModels
    lateinit var adapter:MediaAdapters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            arguments?.let {

                val repos = StatusRepos(requireActivity())
                viewModels= ViewModelProvider(requireActivity(),
                    StatusViewModelFactorys(repos))[StatusViewModels::class.java]
                val mediaType = it.getString(Constants.MEDIA_LIST_KEY, "")
                 when(mediaType){
                     Constants.MEDIA_TYPE_WHATSAPP_IMAGES->{
                         viewModels.whatsAppImagesLiveData.observe(requireActivity()){unFilteredList->
                             val filteredList = unFilteredList.distinctBy { model->
                                 model.fileName
                             }
                             val list=ArrayList<MediaModels>()
                             filteredList.forEach{model->
                                 list.add(model)
                             }
                             adapter = MediaAdapters(list , requireActivity())
                             mediaRecyclerView.adapter = adapter
                             if (list.size == 0){
                                 tempMediaText.visibility = View.VISIBLE
                             }else{
                                 tempMediaText.visibility = View.GONE
                             }
                         }
                     }
                     Constants.MEDIA_TYPE_WHATSAPP_VIDEOS->{
                         viewModels.whatsAppVideosLiveData.observe(requireActivity()){unFilteredList->
                             val filteredList = unFilteredList.distinctBy { model->
                                 model.fileName
                             }

                             val list=ArrayList<MediaModels>()
                             filteredList.forEach{model->
                                 list.add(model)
                             }
                             adapter = MediaAdapters(list , requireActivity())
                             mediaRecyclerView.adapter = adapter
                             if (list.size == 0){
                                 tempMediaText.visibility = View.VISIBLE
                             }else{
                                 tempMediaText.visibility = View.GONE
                             }
                         }
                     }

                     Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES->{
                         viewModels.whatsAppBusinessImagesLiveData.observe(requireActivity()){unFilteredList->
                             val filteredList = unFilteredList.distinctBy { model->
                                 model.fileName
                             }
                             val list=ArrayList<MediaModels>()
                             filteredList.forEach{model->
                                 list.add(model)
                             }
                             adapter = MediaAdapters(list , requireActivity())
                             mediaRecyclerView.adapter = adapter
                             if (list.size == 0){
                                 tempMediaText.visibility = View.VISIBLE
                             }else{
                                 tempMediaText.visibility = View.GONE
                             }
                         }
                     }


                     Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS->{
                         viewModels.whatsAppBusinessVideosLiveData.observe(requireActivity()){unFilteredList->
                             val filteredList = unFilteredList.distinctBy { model->
                                 model.fileName
                             }
                             val list=ArrayList<MediaModels>()
                             filteredList.forEach{model->
                                 list.add(model)
                             }
                             adapter = MediaAdapters(list , requireActivity())
                             mediaRecyclerView.adapter = adapter
                             if (list.size == 0){
                                 tempMediaText.visibility = View.VISIBLE
                             }else{
                                 tempMediaText.visibility = View.GONE
                             }
                         }
                     }
                 }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =binding.root

}