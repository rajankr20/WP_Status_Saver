package com.viewss.fragmentss

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.datas.StatusRepos
import com.devatrii.statussaver.utils.Constants
import com.devatrii.statussaver.utils.SharedPrefKeys
import com.devatrii.statussaver.utils.SharedPrefUtils
import com.example.statussaver.databinding.FragmentStatussBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.utilssz.getFolderPermissions
import com.viewmodelss.StatusViewModels
import com.viewmodelss.factoriess.StatusViewModelFactorys
import com.viewss.adapters.MediaViewPagerAdapters

class FragmentStatuss : Fragment() {

     private val binding by lazy {
         FragmentStatussBinding.inflate(layoutInflater)
     }
     private lateinit var type : String
     private val WHATSAPP_REQUEST_CODE = 101
     private val WHATSAPP_BUSINESS_REQUEST_CODE = 102

    private val viewPagerTitles = arrayListOf("Images", "Videos")
    lateinit var  viewModels: StatusViewModels


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding.apply {
            arguments?.let {

                val repos = StatusRepos(requireActivity())
                viewModels= ViewModelProvider(requireActivity(), StatusViewModelFactorys(repos))[StatusViewModels::class.java]


                type= it.getString(Constants.FRAGMENT_TYPE_KEY, "")

                when(type){
                    Constants.TYPE_WHATSAPP_MAIN->{
                        //check permission
                        //granted then fetch status
                        //get permission
                        // fetch status
                        val  isPermissionGranted = SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, false)
                        if (isPermissionGranted){
                            getWhatsAppStatuses()

                            binding.swipeRefreshLayout.setOnRefreshListener {
                                refreshStatuses()
                            }
                        }
                        permissionLayout.btnPermission.setOnClickListener {
                            getFolderPermissions(
                                context= requireActivity(),
                                REQUEST_CODE = WHATSAPP_REQUEST_CODE,
                                initialUri = Constants.getWhatsappUri())
                        }
                        val  viewPagerAdapters = MediaViewPagerAdapters(requireActivity())
                        statusViewPager.adapter = viewPagerAdapters
                        TabLayoutMediator(tabLayout, statusViewPager){tab,pos ->
                            tab.text = viewPagerTitles[pos]
                        }.attach()
                    }
                    Constants.TYPE_WHATSAPP_BUSINESS->{
                        //check permission
                        //granted then fetch status
                        //get permission
                        // fetch status
                        val  isPermissionGranted = SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED, false)
                        if (isPermissionGranted){
                            getWhatsAppBusinessStatuses()
                            binding.swipeRefreshLayout.setOnRefreshListener {
                                refreshStatuses()
                            }
                        }
                        permissionLayout.btnPermission.setOnClickListener {
                            getFolderPermissions(context= requireActivity(), REQUEST_CODE = WHATSAPP_BUSINESS_REQUEST_CODE,
                                initialUri = Constants.getWhatsappBusinessUri())
                        }

                        val  viewPagerAdapters = MediaViewPagerAdapters(requireActivity(),
                            imageType = Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES,
                            videosType = Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS)
                        statusViewPager.adapter = viewPagerAdapters
                        TabLayoutMediator(tabLayout, statusViewPager){tab,pos ->
                            tab.text = viewPagerTitles[pos]
                        }.attach()
                    }
                }
            }
         }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =binding.root
    fun refreshStatuses() {
        when (type){
            Constants.TYPE_WHATSAPP_MAIN->{
                Toast.makeText(requireActivity(),"Refreshing Wp Statuses",Toast.LENGTH_SHORT).show()
                getWhatsAppStatuses()
            }

            else ->{
                Toast.makeText(
                    requireActivity(),
                    "Refreshing WP Business Statuses",Toast.LENGTH_SHORT).show()
                getWhatsAppBusinessStatuses()
            }
        }
        Handler(Looper.myLooper()!!).postDelayed({
            binding.swipeRefreshLayout.isRefreshing = false
        }, 2000)
    }
     fun getWhatsAppStatuses(){
         //function to get wp statuses
         binding.permissionLayoutHolder.visibility = View.GONE
         viewModels.getWhatsAppStatuses()
     }

    private val TAG = "FragmentStatus"
    fun getWhatsAppBusinessStatuses(){
        //function to get wp statuses
        binding.permissionLayoutHolder.visibility = View.GONE
        Log.d(TAG, "getWhatsAppBusinessStatuses: Getting Wp Business Statuses")
        viewModels.getWhatsAppBusinessStatuses()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== AppCompatActivity.RESULT_OK){
            val treeUri = data?.data!!

            requireActivity().contentResolver.takePersistableUriPermission(
                treeUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (requestCode == WHATSAPP_REQUEST_CODE){
                //Whatsapp  logics here
                SharedPrefUtils.putPrefString(SharedPrefKeys.PREF_KEY_WP_TREE_URI,treeUri.toString())
                SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, true)
                getWhatsAppStatuses()
            }else if (requestCode == WHATSAPP_BUSINESS_REQUEST_CODE){
                //Whatsapp business logics here
                SharedPrefUtils.putPrefString(SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI,treeUri.toString())
                SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED, true)
                getWhatsAppBusinessStatuses()
            }
        }
    }
}