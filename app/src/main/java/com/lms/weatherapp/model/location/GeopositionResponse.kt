package com.lms.weatherapp.model.location

import com.lms.weatherapp.model.common.MetricImperialValues
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GeopositionResponse(
    @field:Json(name = "Version") val version: Int,
    @field:Json(name = "Key") val key: String,
    @field:Json(name = "Type") val type: String,
    @field:Json(name = "Rank") val rank: Int,
    @field:Json(name = "LocalizedName") val localizedName: String,
    @field:Json(name = "EnglishName") val englishName: String,
    @field:Json(name = "PrimaryPostalCode") val primaryPostalCode: String,
    @field:Json(name = "Region") val region: Region,
    @field:Json(name = "Country") val country: Country,
    @field:Json(name = "AdministrativeArea") val adminstrativeArea: AdministrativeArea,
    @field:Json(name = "TimeZone") val timeZone: TimeZone,
    @field:Json(name = "GeoPosition") val geoPosition: GeoPosition,
    @field:Json(name = "IsAlias") val isAlias: Boolean,
    @field:Json(name = "SupplementalAdminAreas") val supplementalAdminAreas: List<AdminArea>,
    @field:Json(name = "DataSets") val dataSets: List<String>,
)

@JsonClass(generateAdapter = true)
data class Region(
    @field:Json(name = "ID") val id : String,
    @field:Json(name = "LocalizedName") val localizedName : String,
    @field:Json(name = "EnglishName") val englishName : String,
)

@JsonClass(generateAdapter = true)
data class Country(
    @field:Json(name = "ID") val id : String,
    @field:Json(name = "LocalizedName") val localizedName : String,
    @field:Json(name = "EnglishName") val englishName : String,
)

@JsonClass(generateAdapter = true)
data class AdministrativeArea(
    @field:Json(name = "ID") val id : String,
    @field:Json(name = "LocalizedName") val localizedName : String,
    @field:Json(name = "EnglishName") val englishName : String,
    @field:Json(name = "Level") val level: Int,
    @field:Json(name = "LocalizedType") val localizedType: String,
    @field:Json(name = "EnglishType") val englishType: String,
    @field:Json(name = "CountryId") val countryId: String,
)

@JsonClass(generateAdapter = true)
data class TimeZone(
    @field:Json(name = "Code") val code : String,
    @field:Json(name = "Name") val name: String,
    @field:Json(name = "GmtOffset") val gtmOffset: Int,
    @field:Json(name = "IsDaylightSaving") val isDaylightSaving: Boolean,
    @field:Json(name = "NextOffsetChange") val nextOffsetChange: String
)

@JsonClass(generateAdapter = true)
data class GeoPosition(
    @field:Json(name = "Latitude") val latitude: Long,
    @field:Json(name = "Longitude") val longitude: Long,
    @field:Json(name = "Elevation") val elevation: MetricImperialValues
)

@JsonClass(generateAdapter = true)
data class AdminArea(
    @field:Json(name = "Level") val level: Int,
    @field:Json(name = "LocalizedName") val localizedName : String,
    @field:Json(name = "EnglishName") val englishName: String
)

