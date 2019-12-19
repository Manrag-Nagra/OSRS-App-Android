package com.example.osrs_tracker

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_grand_exchange.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.osrs_tracker.adapters.GEAdapter
import com.example.osrs_tracker.apiservice.ApiServiceGE
import com.example.osrs_tracker.models.Item
import com.example.osrs_tracker.models.Items


class GrandExchange : AppCompatActivity(){

    var isLoading = false
    var page = 1
    //Contains all the items that are returned
    var listofItems: MutableList<Item> = ArrayList()

    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grand_exchange)

        layoutManager = LinearLayoutManager(this@GrandExchange)
        geRecyclerView.layoutManager = layoutManager

        //When use enter a item in the textview
        geEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            //check if the pressure enter on the keyboard
            if (keyCode == KeyEvent.KEYCODE_ENTER && geEditText.text.isNotEmpty()) {
                //Reset page number
                page = 1
                //Wipe anything in the list
                listofItems = ArrayList()
                //Show indicator
                spinningIndicator.visibility = View.VISIBLE
                //Fetch JSON
                fetchJson(page)
            } else if(keyCode == KeyEvent.KEYCODE_ENTER && geEditText.text.isEmpty()){
               createToast("Please enter an item.")
            }
            false
        })

        geButton.setOnClickListener {
            if (geEditText.text.isNotEmpty()) {
                //Reset page number
                page = 1
                //Wipe anything in the list
                listofItems = ArrayList()
                //Show indicator
                spinningIndicator.visibility = View.VISIBLE
                //Fetch JSON
                fetchJson(page)
            } else if(geEditText.text.isEmpty()){
                createToast("Please enter an item.")
            }
        }

        //Pagination
        geRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if(dy > 0) {
                    //Scroll Vertically
                    val visibleCount = layoutManager.childCount
                    val pastVisible = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = geRecyclerView.adapter?.itemCount
                    if (!isLoading) {
                        //if at the end of the list
                        if ((visibleCount + pastVisible) >= total!!) {
                            page++
                            loadMoreIndicator.visibility = View.VISIBLE
                            fetchJson(page)
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    //Fetch JSON
    fun fetchJson(pageNum : Int){

        isLoading = true
        val url = "http://192.168.2.11:3002/"

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiServiceGE::class.java)

        //Fetch Items
            api.fetchAllItems(geEditText.text.toString().toLowerCase(), pageNum).enqueue(object: Callback<Items> {
                //On Success
                override fun onResponse(call: Call<Items>, response: Response<Items>) {
                    //Turn off indicator
                    spinningIndicator.visibility = View.GONE
                    loadMoreIndicator.visibility = View.GONE
                    isLoading = true

                    Log.d("RetrofitSuccess", response.body().toString())

                    val responseJson = response.body()

                    //Handle if item is not found
                    if (responseJson != null) {

                        //Handle if more items are available
                        if (pageNum > 1 && responseJson.status == "success") {
                            listofItems.addAll(responseJson.items)
                            geRecyclerView.adapter?.notifyDataSetChanged()
                            isLoading = false
                            return
                        } else if(pageNum> 1 && responseJson.status == "failure"){
                            createToast("No more items")
                            return
                        }

                        //Handle if 'failure' is returned in json
                        if (responseJson.status == "failure") {
                            createToast("No items found")
                        } else {
                            //Append response of items to the list
                            //Send response to adapater
                            listofItems = responseJson.items
                            geRecyclerView.adapter =
                                GEAdapter(
                                    listofItems
                                )
                        }
                    }
                    isLoading= false

                }

            //Failure
            override fun onFailure(call: Call<Items>, t: Throwable) {
                spinningIndicator.visibility = View.GONE
                Log.d("RetrofitFailure", t.toString())
                println("Failure")

                isLoading= false
            }

        })

    }


    //Display data and send response to adapter
    private fun createToast(message: String) {
        //Create Toast
        val toast = Toast.makeText(
            this@GrandExchange,
            message,
            Toast.LENGTH_SHORT
        )
        //Change Toast background
        val view = toast.view
        view.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)

        toast.show()

    }



}