package com.lms.weatherapp.model.location

import com.google.gson.annotations.SerializedName
import com.lms.weatherapp.model.common.MetricImperialValues



data class GeopositionResponse(
    @SerializedName("Version") val version: Int,
    @SerializedName("Key") val key: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Rank") val rank: Int,
    @SerializedName("LocalizedName") val localizedName: String,
    @SerializedName("EnglishName") val englishName: String,
    @SerializedName("PrimaryPostalCode") val primaryPostalCode: String,
    @SerializedName("Region") val region: Region,
    @SerializedName("Country") val country: Country,
    @SerializedName("AdministrativeArea") val adminstrativeArea: AdministrativeArea,
    @SerializedName("TimeZone") val timeZone: TimeZone,
    @SerializedName("GeoPosition") val geoPosition: GeoPosition,
    @SerializedName("IsAlias") val isAlias: Boolean,
    @SerializedName("SupplementalAdminAreas") val supplementalAdminAreas: List<AdminArea>,
    @SerializedName("DataSets") val dataSets: List<String>,
)


data class Region(
    @SerializedName("ID") val id : String,
    @SerializedName("LocalizedName") val localizedName : String,
    @SerializedName("EnglishName") val englishName : String,
)


data class Country(
    @SerializedName("ID") val id : String,
    @SerializedName("LocalizedName") val localizedName : String,
    @SerializedName("EnglishName") val englishName : String,
)


data class AdministrativeArea(
    @SerializedName("ID") val id : String,
    @SerializedName("LocalizedName") val localizedName : String,
    @SerializedName("EnglishName") val englishName : String,
    @SerializedName("Level") val level: Int,
    @SerializedName("LocalizedType") val localizedType: String,
    @SerializedName("EnglishType") val englishType: String,
    @SerializedName("CountryId") val countryId: String,
)


data class TimeZone(
    @SerializedName("Code") val code : String,
    @SerializedName("Name") val name: String,
    @SerializedName("GmtOffset") val gtmOffset: Int,
    @SerializedName("IsDaylightSaving") val isDaylightSaving: Boolean,
    @SerializedName("NextOffsetChange") val nextOffsetChange: String
)


data class GeoPosition(
    @SerializedName("Latitude") val latitude: Double,
    @SerializedName("Longitude") val longitude: Double,
    @SerializedName("Elevation") val elevation: MetricImperialValues
)

data class AdminArea(
    @SerializedName("Level") val level: Int,
    @SerializedName("LocalizedName") val localizedName : String,
    @SerializedName("EnglishName") val englishName: String
)

