package com.kyawzinlinn.core_database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kyawzinlinn.core_network.model.City

@Entity("cities")
data class CityEntity(
    @ColumnInfo("country") val country: String,
    @PrimaryKey val id: Int,
    @ColumnInfo("lat") val lat: Double,
    @ColumnInfo("lon") val lon: Double,
    @ColumnInfo("name") val name: String
)

fun City.toCityEntity(): CityEntity {
    return CityEntity(
        country = this.country,
        id = this.id,
        lat = this.lat,
        lon = this.lon,
        name = this.name
    )
}