package com.example.osrs_tracker.models

data class Items(
    val status: String,
    val items: MutableList<Item>
)

data class Item(
    val name: String,
    val id: Int,
    val icon: String,
    val icon_large: String,
    val description: String,
    val members: Boolean,
    val current: Current,
    val today: Today
)

data class Current(
    val currentTrend: String,
    val currentPrice: String
)

data class Today(
    val todayTrend: String,
    val todayPrice: String
)