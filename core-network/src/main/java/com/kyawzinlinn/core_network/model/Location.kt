package com.kyawzinlinn.core_network.model

import com.google.gson.annotations.SerializedName

data class Location(
    val country: String,
    val lat: String,
    val localtime: String,
    val lon: String,
    val name: String,
    val region: String
)