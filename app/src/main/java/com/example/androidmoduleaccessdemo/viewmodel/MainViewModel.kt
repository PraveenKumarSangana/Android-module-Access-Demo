package com.example.androidmoduleaccessdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoduleaccessdemo.model.UserDataWrapper
import com.example.androidmoduleaccessdemo.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getUserData(): MutableLiveData<UserDataWrapper> {
        val mutableLiveData = MutableLiveData<UserDataWrapper>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainRepository.getUserDataFromAssets()
            mutableLiveData.postValue(response)
        }
        return mutableLiveData
    }

}