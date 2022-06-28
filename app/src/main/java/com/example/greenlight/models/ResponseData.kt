package com.example.greenlight.models

data class ResponseData(
    val sales_area: List<SalesArea>,
    val sales_country: List<SalesCountry>,
    val sales_region: List<SalesRegion>,
    val sales_zone: List<SalesZone>
)