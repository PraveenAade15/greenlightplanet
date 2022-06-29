package com.example.greenlight.repo

import androidx.lifecycle.MutableLiveData
import com.example.greenlight.api.ApiService
import com.example.greenlight.models.AreaResponse
import com.example.greenlight.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class AreaRepo @Inject constructor(
    private val apiService: ApiService

) {
    private val _areaLiveData = MutableLiveData<NetworkResult<AreaResponse>>()
    val areaLiveData get() = _areaLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData

    suspend fun getAreaList() {
        _areaLiveData.postValue(NetworkResult.Loading())
        val response = apiService.getArea()

        if (response.isSuccessful && response.body() != null) {
            _areaLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _areaLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _areaLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}