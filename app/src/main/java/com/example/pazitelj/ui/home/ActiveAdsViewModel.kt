package com.example.pazitelj.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazitelj.models.Ad
import com.example.pazitelj.models.AdFilter
import com.example.pazitelj.network.TestApi
import kotlinx.coroutines.launch

class ActiveAdsViewModel : ViewModel() {

    private val _owAds = MutableLiveData<List<Ad>>()
    val owAds: LiveData<List<Ad>> = _owAds

    private val _appAds = MutableLiveData<List<Ad>>()
    val appAds: LiveData<List<Ad>> = _appAds


    init {
        //getOwAds(AdFilter(Type = ))
    }
    fun getOwAds(filter: AdFilter) {
        viewModelScope.launch {
            try {
                _owAds.value = TestApi.retrofitService.GetAds(Type = filter.Type, CurrentUser = filter.CurrentUser, UserId = filter.UserId, ShowAppliedCount = filter.ShowAppliedCount, ShowStatus = filter.ShowStatus)
                Log.d("owadsstatus","good")
                reset()
            }catch (e: Exception) {
                Log.d("owadsfail",e.message!!)
            }
        }
    }

    fun getAppAds(filter: AdFilter) {
        viewModelScope.launch {
            try {
                _owAds.value = TestApi.retrofitService.GetAds(Type = filter.Type, CurrentUser = filter.CurrentUser, UserId = filter.UserId, ShowAppliedCount = filter.ShowAppliedCount, ShowStatus = filter.ShowStatus)
                Log.d("owadsstatus","good")
                reset()
            }catch (e: Exception) {
                Log.d("owadsfail",e.message!!)
            }
        }
    }

    fun reset(){
        val applist = _appAds.value
        if(!applist.isNullOrEmpty()) {
            _appAds.value = emptyList()
            _appAds.value = applist!!
        }

            val owlist = _owAds.value
            if(!owlist.isNullOrEmpty()){
                _owAds.value = emptyList()
                _owAds.value = owlist!!
        }

    }

    fun getAdAt(index: Int): Ad {
        return _appAds.value!![index]
    }

    fun appliedUsers(count: Int) : String{
        if (count == 0){
            return "No applied users"
        }
        else{
            return "$count applied users"
        }
    }
}