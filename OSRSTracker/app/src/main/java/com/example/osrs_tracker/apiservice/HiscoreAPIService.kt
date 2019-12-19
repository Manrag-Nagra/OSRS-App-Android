package com.example.osrs_tracker.apiservice

import com.example.osrs_tracker.models.Hiscores
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceHiscores {
    @GET( "hiscores/{name}")
    fun fetchAllStats(@Path("name") playerName: String): Call<Hiscores>
}