package com.example.osrs_tracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        geButton.setOnClickListener {
            startActivity(Intent(this, GrandExchange::class.java))
        }

        hiscoreButton.setOnClickListener {
            startActivity(Intent(this, HiScores::class.java))
        }


    }
}
