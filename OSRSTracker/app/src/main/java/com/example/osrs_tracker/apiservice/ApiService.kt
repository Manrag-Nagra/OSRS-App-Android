package com.example.osrs_tracker.apiservice

import com.example.osrs_tracker.models.Hiscores
import com.example.osrs_tracker.models.Items
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceGE {
    @GET( "items/{item}/{page}")
    fun fetchAllItems(@Path("item") itemName: String, @Path("page") pageNum: Int): Call<Items>
}