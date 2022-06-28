package com.example.greenlight.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenlight.repo.AreaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AreaViewModel @Inject constructor(private val arearepo: AreaRepo) : ViewModel() {

    val areaLiveData get() = arearepo.areaLiveData

    //    val statusLiveData get() = arearepo.statusLiveData
    fun getAllArea() {
        viewModelScope.launch {
            arearepo.getAreaList()
        }


    }
}