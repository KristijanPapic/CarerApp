package com.example.pazitelj.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazitelj.models.AdInput
import com.example.pazitelj.models.Pet
import com.example.pazitelj.models.User
import com.example.pazitelj.network.TestApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CurrentUserViewModel: ViewModel() {
    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user



    fun setBio(newBio: String){
        _user.value!!.Bio = newBio
    }
    fun setPhone(newPhone: String){
        _user.value!!.PhoneNumber = newPhone
    }

    suspend fun getUser(id: String) {
    Log.d("loginin",id)
        try {
            val result = TestApi.retrofitService.getUser(id)
            //Log.d("loginuserres", _user.value!!.Username)
            setResult(result)
        } catch (e: Exception) {
            Log.d("loginuserfail", e.toString())
        }
    }

    private suspend fun setResult(result: User) {
        withContext(Dispatchers.Main) {
            _user.value = result
            Log.d("loginuserresult",user.value.toString())
        }


    }
     fun updateUser(){
        viewModelScope.launch {
            try {
                TestApi.retrofitService.updateUser(_user.value!!)
                Log.d("updatesucces","ok")
            }catch (e: Exception) {
                Log.d("updatefail",e.message!!)
            }
        }
    }

     suspend fun addPet(pet: Pet){
            try {
                TestApi.retrofitService.addPet(pet)
                _user.value!!.Pets.add(pet)
                Log.d("added pet","added")
            }
            catch (e: Exception){
                Log.d("petfail","fail")
        }

    }
    suspend fun deletePet(position: Int){
        try {
            TestApi.retrofitService.deletePet(user.value!!.Pets[position].Id!!)
            _user.value!!.Pets.removeAt(position)
            Log.d("delete pet","added")
        }
        catch (e: Exception){
            Log.d("deletepetfail","fail")
        }

    }

    suspend fun postAd(ad: AdInput){
        try{
            TestApi.retrofitService.PostAd(ad)
            Log.d("post ad","posted")
        }
        catch (e: Exception){
            Log.d("adfail","fail")
        }
    }
}