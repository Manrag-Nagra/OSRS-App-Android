package com.example.osrs_tracker

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.osrs_tracker.adapters.HiscoreAdapter
import com.example.osrs_tracker.apiservice.ApiServiceGE
import com.example.osrs_tracker.apiservice.ApiServiceHiscores
import com.example.osrs_tracker.models.Hiscores
import com.example.osrs_tracker.models.Stats
import kotlinx.android.synthetic.main.activity_grand_exchange.*
import kotlinx.android.synthetic.main.activity_hiscores.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class HiScores : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hiscores)

        layoutManager = LinearLayoutManager(this@HiScores)
        statsRecycleView.layoutManager = layoutManager


        hiscoresButton.setOnClickListener {
            if (hiscoreEditText.text.isNotEmpty()) {
                hiscoreIndicator.visibility = View.VISIBLE
                fetchPlayerStats()
            } else if(hiscoreEditText.text.isEmpty()){
                createToast("Please enter player name")
            }
        }
    }


    fun fetchPlayerStats(){

        val url = "<URL HERE>"

        val retrofit = Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiServiceHiscores::class.java)

        api.fetchAllStats(hiscoreEditText.text.toString()).enqueue(object: Callback<Hiscores> {
            override fun onResponse(call: Call<Hiscores>, response: Response<Hiscores>) {
                //Turn off indicator
                hiscoreIndicator.visibility = View.GONE

                Log.d("Retrofit", response.body().toString())

                val responseJSON = response.body()

                //Handle if item is not found
                if(responseJSON != null) {
                    //Handle if 'failure' is returned in json
                    if (responseJSON.status == "failure") {
                        createToast("Cannot find player")
                    } else {
                        //Send response to adapter
                        statsRecycleView.adapter = HiscoreAdapter(responseJSON.stats)
                    }
                }

            }

            override fun onFailure(call: Call<Hiscores>, t: Throwable) {
               Log.d("Retrofit", t.toString())
        }

        })

    }


    //Display data and send response to adapter
    private fun createToast(message: String) {
        //Create Toast
        val toast = Toast.makeText(
            this@HiScores,
            message,
            Toast.LENGTH_SHORT
        )
        //Change Toast background
        val view = toast.view
        view.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)

        toast.show()

    }

}