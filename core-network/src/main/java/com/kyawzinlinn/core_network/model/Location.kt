package com.kyawzinlinn.core_network.model

import com.google.gson.annotations.SerializedName

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val lon: Double,
    val name: String,
    val region: String,
    @SerializedName("tz_id") val timezoneId: String
)