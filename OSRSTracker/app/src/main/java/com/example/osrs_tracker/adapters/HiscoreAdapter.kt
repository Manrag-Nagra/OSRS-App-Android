package com.example.osrs_tracker.adapters

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.osrs_tracker.HiScores
import com.example.osrs_tracker.R
import com.example.osrs_tracker.models.Stats
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ge_row.view.*
import kotlinx.android.synthetic.main.hiscore_row.view.*
import retrofit2.http.Url
import java.io.File
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class HiscoreAdapter(private val hiscore: List<Stats>): RecyclerView.Adapter<CustomHiScoreViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHiScoreViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.hiscore_row, parent, false)
        return CustomHiScoreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hiscore.size
    }

    override fun onBindViewHolder(holder: CustomHiScoreViewHolder, position: Int) {
        val hiscores = hiscore[position]
        holder.view.hiscoreSkill.text = hiscores.skill
        holder.view.hiscoreLevel.text = hiscores.level.toString()
        holder.view.hiscoreRank.text = hiscores.rank.toString()
        holder.view.hiscoreXP.text = hiscores.experience.toString()
    }

}

class CustomHiScoreViewHolder(val view: View): RecyclerView.ViewHolder(view){

}