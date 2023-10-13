package com.viewss.fragmentss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.statussaver.R
import com.example.statussaver.databinding.FragmentSettingssBinding
import com.modelss.SettingsModels
import com.viewss.adapters.SettingsAdapter


class FragmentSettingss : Fragment() {
    private val binding by lazy {
        FragmentSettingssBinding.inflate(layoutInflater)
    }
    private val list = ArrayList<SettingsModels>()
    private val adapters by lazy {
        SettingsAdapter(list, requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding.apply {
             settingsRecyclerView.adapter = adapters
             list.add(
                SettingsModels(
                    title = "howt to use",
                    desc = "know how to download statuses"
                )

             )
             list.add(
                 SettingsModels(
                     title = "Save in Folder",
                     desc = "/internalstorage/Documents/${getString(R.string.app_name)}"
                 )
             )
             list.add(
                 SettingsModels(
                     title = "Disclaimer",
                     desc = "Read Our Disclaimer"
                 )
             )
             list.add(
                 SettingsModels(
                     title = "Privacy Policy",
                     desc = "Read Our Terms & Conditions"
                 )
             )
             list.add(
                 SettingsModels(
                     title = "Share",
                     desc = "Sharing is caring"
                 )
             )
             list.add(
                 SettingsModels(
                     title = "Rate Us",
                     desc = "Please support our work by rating on PlayStore"
                 )
             )

         }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =binding.root
}