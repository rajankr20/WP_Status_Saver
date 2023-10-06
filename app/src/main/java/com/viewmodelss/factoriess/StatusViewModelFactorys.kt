package com.viewmodelss.factoriess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.datas.StatusRepos
import com.viewmodelss.StatusViewModels

class StatusViewModelFactorys(private val repos: StatusRepos):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
         return StatusViewModels(repos)as T
    }


}