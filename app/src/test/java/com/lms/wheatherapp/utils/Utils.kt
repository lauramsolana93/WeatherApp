package com.lms.wheatherapp.utils

import com.lms.weatherapp.model.common.Imperial
import com.lms.weatherapp.model.common.Metric
import com.lms.weatherapp.model.common.MetricImperialValues
import com.lms.weatherapp.model.location.*

fun getGeoPositionMock(): GeopositionResponse = GeopositionResponse(
    version = 1,
    key = "307301",
    type = "",
    rank = 0,
    localizedName = "Sabadell",
    englishName = "Sabadell",
    primaryPostalCode = "08202",
    region = Region("", "Catalonia", "Catalonia"),
    country = Country("", "Spain", "Spain"),
    adminstrativeArea = AdministrativeArea("", "Barcelona",
        "Barcelona", 0, "", "", "" ),
    timeZone = TimeZone("GTM", "European", 1,
        true, ""),
    geoPosition = GeoPosition(
        4154329L,
        210942L,
        elevation = MetricImperialValues(Metric(2, "", 1), Imperial(2, "", 1))
    ),
    isAlias = true,
    supplementalAdminAreas = listOf(),
    dataSets = listOf()
)


