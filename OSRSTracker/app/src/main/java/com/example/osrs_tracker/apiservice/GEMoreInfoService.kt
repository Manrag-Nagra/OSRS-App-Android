package com.example.osrs_tracker.apiservice

import com.example.osrs_tracker.models.GEMoreInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GEMoreInfoService {
    @GET( "itemsDetail/{id}")
    fun fetchAllStats(@Path("id") itemID: Int): Call<GEMoreInfo>
}