package com.example.osrs_tracker

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.osrs_tracker.apiservice.GEMoreInfoService
import com.example.osrs_tracker.models.GEMoreInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ge_more_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GEMoreInfo(): AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ge_more_info)

        val itemID = intent.getIntExtra("itemID", 0)
        val itemName = intent.getStringExtra("itemName")

        setTitle((itemName))

        moreInfoSpinner.visibility = View.VISIBLE

        fetchMoreItemInfo(itemID)
    }


    fun fetchMoreItemInfo(itemID: Int) {

        val url = "<URL HERE>"

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(GEMoreInfoService::class.java)

        api.fetchAllStats(itemID).enqueue(object : Callback<GEMoreInfo> {
            override fun onResponse(call: Call<GEMoreInfo>, response: Response<GEMoreInfo>) {

                moreInfoSpinner.visibility = View.GONE
                moreInfoContraint.visibility = View.VISIBLE

                Log.d("RetrofitSuccess", response.body().toString())

                val responseJson = response.body()

                //Handle if item is not found
                if (responseJson != null) {
                    if (responseJson.status == "failure") {
                        createToast("Can't find more detail")
                    } else {

                        //extract just the item
                        val item = responseJson.item

                        //load images
                        val iconImage = imageView
                        Picasso.get().load(item.icon_large).into(iconImage);

                        //Assign textview to json values
                        titleView.text = item.name
                        descriptionView.text = item.description

                        //Members
                        if(item.members){
                            cripsy.text = "True"
                        }else {
                            cripsy.text = "False"
                        }

                        //Current
                        currentTrendView.text = item.current.currentTrend
                        currentPriceView.text = item.current.currentPrice

                        //Price
                        todayTrendView.text = item.today.todayTrend
                        todayPriceView.text = item.today.todayPrice

                        //Last 30 Days
                        last30DaysTrendView.text = item.last30Days.day30Trend
                        last30DaysPriceView.text = item.last30Days.day30Change

                        //Last 90 Days
                        last90DaysTrendView.text = item.last90Days.day90Trend
                        last90DaysPriceView.text = item.last90Days.day90Change

                        //Last 180 Days
                        last180DaysTrendView.text = item.last180Days.day180Trend
                        last180DaysPriceView.text = item.last180Days.day180Change
                    }

                }


            }

            override fun onFailure(call: Call<GEMoreInfo>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    //Display data and send response to adapter
    private fun createToast(message: String) {
        //Create Toast
        val toast = Toast.makeText(
            this@GEMoreInfo,
            message,
            Toast.LENGTH_SHORT
        )
        //Change Toast background
        val view = toast.view
        view.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)

        toast.show()

    }
}
