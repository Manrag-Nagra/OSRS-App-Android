package com.example.osrs_tracker.models

data class Hiscores(
    val status: String,
    val stats: List<Stats>
)

data class Stats(
    val skill: String,
    val rank: Int,
    val level: Int,
    val experience: Int
)
