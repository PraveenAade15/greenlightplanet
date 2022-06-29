package com.example.greenlight.callbacks

import com.example.greenlight.models.AreaResponse
import com.example.greenlight.models.SalesCountry

interface CountryClickListener {
    //call back
    fun clickOnArea(areaResponse: com.example.greenlight.database.AreaResponse)
    fun onStrValue(str:String)

}