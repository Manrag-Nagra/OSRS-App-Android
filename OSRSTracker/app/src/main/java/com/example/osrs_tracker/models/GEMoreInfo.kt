package com.example.osrs_tracker.models

data class GEMoreInfo(
    val status: String,
    val item: MoreInfo
)

data class MoreInfo(
    val name: String,
    val id: Int,
    val icon: String,
    val icon_large: String,
    val description: String,
    val members: Boolean,
    val current: Current,
    val today: Today,
    val last30Days: last30Days,
    val last90Days: last90Days,
    val last180Days: last180Days
)

data class last30Days(
    val day30Trend: String,
    val day30Change: String
)

data class last90Days(
    val day90Trend: String,
    val day90Change: String
)

data class last180Days(
    val day180Trend: String,
    val day180Change: String
)